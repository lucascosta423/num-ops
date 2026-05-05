package com.main.numOps.domain.did.dtos;

import com.main.numOps.Enuns.DidStatus;
import com.main.numOps.domain.did.DidModel;

import java.time.LocalDateTime;

public record DidWithoutDidDTO(
        Integer id,
        String razao,
        String document,
        String cep,
        String logradouro,
        String numeroEndereco,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        String providerNome,
        LocalDateTime createdAt,
        DidStatus status
) {
    public static DidWithoutDidDTO fromEntity(DidModel model){
        return new DidWithoutDidDTO(
                model.getId(),
                model.getRazao(),
                model.getDocument(),
                model.getCep(),
                model.getLogradouro(),
                model.getNumeroEndereco(),
                model.getComplemento(),
                model.getBairro(),
                model.getCidade(),
                model.getUf(),
                model.getProvider().getNome(),
                model.getCreatedAt(),
                model.getStatus()
        );
    }
}
