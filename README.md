# NumOps API

API REST para gestão operacional de numeração telefônica, com controle de DIDs disponíveis/configurados, clientes, portabilidade, tickets, operadoras, provedores e usuários.

## 1) Visão Geral

O projeto foi construído com Spring Boot e PostgreSQL, usando autenticação JWT e autorização por perfis (`ADMIN`, `USER`, `SUPER_ADMIN`).

Principais capacidades:

- Autenticação e emissão de token JWT.
- Cadastro e gestão de usuários e provedores.
- Upload e gestão de base de DIDs disponíveis.
- Ativação de DIDs para clientes.
- Gestão de tickets com upload/download de fatura.
- Consulta e atualização de portabilidade.
- Upload e consulta de base de operadoras.

## 2) Stack Tecnológica

- Java 21
- Spring Boot 3.3.9
- Spring Web
- Spring Data JPA
- Spring Security
- PostgreSQL
- Flyway (migrações)
- Springdoc OpenAPI / Swagger UI
- Lombok
- Maven Wrapper (`./mvnw`)

## 3) Estrutura do Projeto

```
src/main/java/com/main/numOps
├── config                # segurança, token JWT, swagger
├── domain                # módulos de negócio (auth, user, provider, dids, etc.)
├── dtos                  # DTOs compartilhados
├── exeptions             # tratamento global de erros
├── mapper                # mapeadores
└── utils                 # utilitários

src/main/resources
├── application.yaml
└── db/migration
    ├── V1__create_initial_schema.sql
    └── V2__insert_data.sql
```

## 4) Banco de Dados e Migrações

As tabelas são criadas pelo Flyway em `V1__create_initial_schema.sql`:

- `provider`
- `usuario`
- `tickets`
- `did_available`
- `customer`
- `did`
- `portability`
- `operators`

`V2__insert_data.sql` cria carga inicial com:

- Provedor `Ultracom`
- Usuário admin `admin@ultracom.com` (senha padrão: `123456`, já hash no banco)

## 5) Configuração de Ambiente

Arquivo: `src/main/resources/application.yaml`

Variáveis necessárias:

- `DB_URL` (ex.: `localhost:5432`)
- `DB_USER`
- `DB_PASSWORD`
- `JWT_SECRET` (opcional; fallback: `my-secret-key`)

Configurações relevantes:

- `spring.jpa.hibernate.ddl-auto=validate`
- Flyway habilitado
- Upload multipart até 50MB
- Armazenamento local em `./storage/faturas`

## 6) Como Executar

### 6.1 Pré-requisitos

- Java 21
- PostgreSQL ativo e acessível

### 6.2 Passos

1. Defina as variáveis de ambiente:

```bash
export DB_URL=localhost:5432
export DB_USER=seu_usuario
export DB_PASSWORD=sua_senha
export JWT_SECRET=segredo_jwt
```

2. Execute a aplicação:

```bash
./mvnw spring-boot:run
```

3. Acesse:

- API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## 7) Autenticação e Autorização

### 7.1 Login

Endpoint público:

- `POST /auth/login`

Exemplo de body:

```json
{
  "login": "admin@ultracom.com",
  "password": "123456"
}
```

Resposta contém `token` JWT.

### 7.2 Uso do token

Enviar no header:

`Authorization: Bearer <token>`

### 7.3 Regras de acesso (Security)

- Público:
  - `POST /auth/login`
  - Swagger (`/v3/api-docs/**`, `/swagger-ui/**`, `/swagger-ui.html`)
- `ADMIN`:
  - gerenciamento de `provedor`, `usuario`, `ticket`, operações de `numero` e `operadoras`
  - `PUT /portabilidade/**`
- `ADMIN` e `USER`:
  - operações em `numero` e `portabilidade` (GET/POST específicos)
- Demais rotas exigem autenticação.

Observação: os mapeamentos de segurança usam alguns paths legados (`/numero/**`, `/portabilidade/**`) e parte dos controllers atuais está em `/did`, `/dids` e `/portability`.

## 8) Endpoints Principais

## AUTH

- `POST /auth/login`

## USER (`/usuario`)

- `POST /save/user`
- `GET /listAll`
- `PUT /update/{id}`
- `DELETE /delete/{id}`

## PROVIDER (`/provedor`)

- `POST /save`
- `GET /listAll`
- `GET /{id}`
- `PUT /update/{id}`
- `DELETE /delete/{id}`

## CUSTOMER (`/customer`)

- `POST /`
- `GET /`
- `PUT /{id}`

## DIDs AVAILABLE (`/dids`)

- `GET /available` (filtro por `area` e `uf`)
- `GET /`
- `POST /filter`
- `PATCH /`
- `POST /upload` (multipart CSV/TXT separado por `;`)

## DIDs CONFIGURED (`/did`)

- `POST /activate`
- `GET /`
- `GET /customer/{id}`

## TICKET (`/ticket`)

- `GET /`
- `POST /` (multipart com dados do ticket + arquivo)
- `PATCH /cancel/{id}`
- `GET /{id}/fatura` (download PDF)

## PORTABILITY (`/portability`)

- `GET /`
- `PUT /update`

## OPERATORS (`/operadoras`)

- `POST /upload`
- `GET /list`
- `GET /numero`

## 9) Formato de Erros

O projeto possui `GlobalExceptionHandler` com padrão de resposta:

- validação (`400`)
- regra de negócio (`400`)
- integridade de dados (`409`)
- recurso não encontrado (`404`)
- JSON inválido/enum inválido (`400`)
- falha interna (`500`)

Em geral, as respostas usam envelope `ApiResponse` com `message`, `status`, `data` e/ou `errors`.

## 10) Coleção de Testes de API

Existe uma coleção Insomnia na raiz:

- `numops_collection.yaml`

Ela já contém rotas de autenticação, número e ticket, incluindo captura automática do token no login.

## 11) Pontos de Atenção Técnicos

- Há inconsistência nominal entre paths em PT/EN (`/portabilidade` vs `/portability`, `/numero` vs `/did` e `/dids`) que pode impactar regras de segurança e clientes da API.
- O endpoint `GET /operadoras/numero` usa `RequestBody` em GET, o que nem sempre é bem suportado por clientes/proxies.
- Existe pasta `exeptions` com grafia diferente do padrão (`exceptions`), sem impacto funcional, mas com impacto de padronização.

## 12) Próximos Passos Recomendados

1. Padronizar nomenclatura de rotas (idioma e pluralização).
2. Alinhar `SecurityConfigurations` com os paths efetivos dos controllers.
3. Adicionar testes automatizados (unitários e integração) para fluxos críticos:
   - login e autorização
   - upload de DIDs
   - ativação de DID
   - criação/cancelamento de ticket
4. Versionar API (`/api/v1`) para reduzir risco em mudanças futuras.

