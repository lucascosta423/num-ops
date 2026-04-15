package com.main.numOps.dtos.user;

import com.main.numOps.Enuns.Status;
import com.main.numOps.domain.user.UserModel;

import java.util.UUID;

public record ResponseUsuarioDto(
        UUID id,
        String nome,
        String email,
        String role,
        Status status,
        String provider
) {
    public static ResponseUsuarioDto fromEntity(UserModel usuario) {
        return new ResponseUsuarioDto(
                usuario.getId(),
                usuario.getName(),
                usuario.getEmail(),
                usuario.getRole().name(),
                usuario.getStatus(),
                usuario.getProvider()  != null ? usuario.getProvider().getNome() : ""
        );
    }
}
