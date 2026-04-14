package com.main.numOps.dtos.Number;

import com.main.numOps.Enuns.Status;
import com.main.numOps.domain.baseDids.DidsModel;

import java.time.LocalDateTime;

public record ResponseAllNumbersDto(
        Integer id,
        String numero,
        String area,
        String cliente,
        String documento,
        LocalDateTime dataAtivacao,
        LocalDateTime dataSolicitacao,
        LocalDateTime dataResevada,
        String provedor,
        Status status
) {
    public static ResponseAllNumbersDto fromEntity(DidsModel didsModel) {
        return new ResponseAllNumbersDto(
                didsModel.getId(),
                didsModel.getNumero(),
                didsModel.getArea(),
                didsModel.getCliente(),
                didsModel.getDocumento(),
                didsModel.getDataAtivacao(),
                didsModel.getDataSolicitacao(),
                didsModel.getDataResevada(),
                didsModel.getProvedor()  != null ? didsModel.getProvedor().getNome() : "",
                didsModel.getStatus()
        );
    }
}
