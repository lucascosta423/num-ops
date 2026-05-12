package com.main.numOps.domain.operators.dtos;

import com.main.numOps.domain.operators.OperatorsModel;

public record CarrierResponse(
    String municipio,
    Integer cnlMunicipio,
    String areaLocal){
    public static CarrierResponse fromEntity(OperatorsModel operadora) {
        return new CarrierResponse(
                operadora.getNomeLocalidade(),
                operadora.getCodigoCnl(),
                operadora.getAreaLocal()
        );
    }
}
