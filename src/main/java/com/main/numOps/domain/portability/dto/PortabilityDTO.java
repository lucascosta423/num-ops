package com.main.numOps.domain.portability.dto;

import com.main.numOps.Enuns.StatusPortability;
import com.main.numOps.domain.portability.PortabilityModel;

public record PortabilityDTO (
        Long id,
        String numero,
        String municipio,
        Integer cnlMunicipo,
        String areaLocal,
        Integer cnlAreLocal,
        String provider,
        String ticket,
        String dataAgendamento,
        String horaAgendamento,
        StatusPortability status
){
    public static PortabilityDTO fromEntity(PortabilityModel model){
        return new PortabilityDTO(
                model.getId(),
                model.getNumero(),
                model.getMunicipio(),
                model.getCnlMunicipo(),
                model.getAreaLocal(),
                model.getCnlAreLocal(),
                model.getProvider().getNome(),
                model.getTicket().getTicket(),
                model.getDataAgendamento(),
                model.getHoraAgendamento(),
                model.getStatus()
        );
    }
}
