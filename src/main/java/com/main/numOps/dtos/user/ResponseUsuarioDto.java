package com.main.numOps.dtos.user;

import com.main.numOps.Enuns.StatusNumber;
import com.main.numOps.domain.user.UserModel;

import java.util.UUID;

public record ResponseUsuarioDto(
        UUID id,
        String nome,
        String email,
        String role,
        StatusNumber statusNumber,
        String provider
) {
    public static ResponseUsuarioDto fromEntity(UserModel usuario) {
        return new ResponseUsuarioDto(
                usuario.getId(),
                usuario.getName(),
                usuario.getEmail(),
                usuario.getRole().name(),
                usuario.getStatusNumber(),
                usuario.getProvider()  != null ? usuario.getProvider().getNome() : ""
        );
    }
}
