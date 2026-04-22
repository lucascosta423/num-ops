package com.main.numOps.mapper;

import com.main.numOps.Enuns.StatusPortability;
import com.main.numOps.domain.Number.portability.PortabilityModel;
import com.main.numOps.domain.operators.OperatorsService;
import com.main.numOps.domain.operators.dtos.CarrierResponse;
import com.main.numOps.domain.ticket.TicketModel;
import org.springframework.stereotype.Component;

@Component
public class PortabilityMapper {

    private final OperatorsService operatorsService;

    public PortabilityMapper(OperatorsService operatorsService) {
        this.operatorsService = operatorsService;
    }

    public PortabilityModel toModel(String numero, TicketModel ticket){
        PortabilityModel portabilityModel = new PortabilityModel();

        var number = findByNumber(numero);

        portabilityModel.setNumero(numero);
        portabilityModel.setMunicipio(number.municipio());
        portabilityModel.setCnlMunicipo(number.cnlMunicipio());
        portabilityModel.setAreaLocal(number.areaLocal());
        portabilityModel.setCnlAreLocal(number.cnlAreLocal());
        portabilityModel.setTicket(ticket);
        portabilityModel.setStatus(StatusPortability.PENDING);

        return portabilityModel;


    }

    private CarrierResponse findByNumber(String numero) {
        return operatorsService.findByNumber(
                numero.substring(0, 2),
                numero.substring(2, 6),
                numero.substring(6)
        );
    }
}
