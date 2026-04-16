package com.main.numOps.domain.Number.did.dtos;

import com.main.numOps.Enuns.StatusNumber;
import com.main.numOps.domain.Number.did.NumberModel;

import java.time.LocalDateTime;

public record NumberResponse(
        Integer id,
        String numero,
        String area,
        String ufArea,
        String provedor,
        String cliente,
        String documento,
        String cep,
        String logradouro,
        String numeroEndereco,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        LocalDateTime dateAtivacao,
        StatusNumber statusNumber
) {
    public static NumberResponse fromEntity(NumberModel n) {
        return new NumberResponse(
                n.getId(),
                n.getNumero(),
                n.getArea(),
                n.getUfArea(),
                n.getProvider().getNome(),
                n.getCliente(),
                n.getDocumento(),
                n.getCep(),
                n.getLogradouro(),
                n.getNumeroEndereco(),
                n.getComplemento(),
                n.getBairro(),
                n.getCidade(),
                n.getUf(),
                n.getDataAtivacao(),
                n.getStatusNumber()
        );
    }
}
