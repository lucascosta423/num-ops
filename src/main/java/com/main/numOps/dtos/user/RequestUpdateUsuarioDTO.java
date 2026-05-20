package com.main.numOps.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class RequestUpdateUsuarioDTO {

        String nome;

        @Email(message = "O e-mail deve ser válido")
        String email;

        String senha;

}
