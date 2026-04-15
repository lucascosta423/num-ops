package com.main.numOps.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestSaveUsuarioDTO(

        @NotBlank(message = "Nome não pode ser vazio ou nulo")
        String nome,

        @Email(message = "O e-mail deve ser válido")
        @NotBlank(message = "Email não pode ser vazio")
        String email,

        @NotBlank(message = "Senha não pode ser vazia")
        String senha,

        @NotNull(message = "Id do provedor não pode ser vazio")
        Integer provedor,

        @NotBlank(message = "Tipo do usuario nao pode ser vazio")
        String role
) {
}
