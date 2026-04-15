package com.main.numOps.dtos.Portability;

import com.main.numOps.Enuns.StatusNumber;
import com.main.numOps.domain.requestPortability.NumberForPortabilityModel;
import com.main.numOps.domain.requestPortability.RequestPortabilityModel;

import java.time.LocalDateTime;
import java.util.List;

public record ResponsePortabilityDTO(
        String id,
        String razao,
        String documento,
        String fatura,
        String usuario,
        String provedor,
        LocalDateTime dataCriado,
        LocalDateTime dataFinalizado,
        StatusNumber statusNumber,
        List<NumberForPortabilityModel> solicitacoes
) {
    public static ResponsePortabilityDTO fromEntity(RequestPortabilityModel portabilidadeModel) {
        return new ResponsePortabilityDTO(
                portabilidadeModel.getId(),
                portabilidadeModel.getRazao(),
                portabilidadeModel.getDocumento(),
                portabilidadeModel.getFileFatura(),
                portabilidadeModel.getUsuario().getName(),
                portabilidadeModel.getProvedor().getNome(),
                portabilidadeModel.getDataCriado(),
                portabilidadeModel.getDataFinalizado(),
                portabilidadeModel.getStatusNumber(),
                portabilidadeModel.getNumberForPortabilityModel()
        );
    }
}
