-- Inserir provider Ultracom
INSERT INTO provider (
    nome,
    contato,
    documento,
    email,
    data_criado,
    data_atualizacao,
    status_number
) VALUES (
             'Ultracom',
             '27999999999',
             '12345678000199',
             'contato@ultracom.com',
             NOW(),
             NOW(),
             'ACTIVE'
         );

-- Inserir usuário ADMIN vinculado ao provider Ultracom
INSERT INTO usuario (
    id,
    name,
    email,
    password,
    role,
    data_criado,
    status_number,
    provider_id
) VALUES (
             gen_random_uuid(), -- se estiver usando PostgreSQL com pgcrypto
             'Administrador',
             'admin@ultracom.com',
             '$2a$12$4ZW9/SlQeAqyEydvpg7GIu29bViFGAbHvlM/4JBS5EUNBslbDzkRq', -- senha criptografada (exemplo: 123456)
             'ADMIN',
             NOW(),
             'ACTIVE',
             (SELECT id FROM provider WHERE nome = 'Ultracom')
         );