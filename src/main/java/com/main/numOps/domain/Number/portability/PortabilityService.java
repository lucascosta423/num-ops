package com.main.numOps.domain.Number.portability;

import com.main.numOps.Enuns.StatusPortability;
import com.main.numOps.domain.ticket.TicketModel;
import com.main.numOps.dtos.Portability.UpdateNumberForPortabilityDTO;
import com.main.numOps.exeptions.NotFoundException;
import com.main.numOps.domain.operators.OperatorsModel;
import com.main.numOps.domain.operators.OperatorsService;
import com.main.numOps.utils.responseApi.SucessResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PortabilityService {
    private final PortabilityRepository portabilityRepository;
    private final OperatorsService operatorsService;

    public PortabilityService(PortabilityRepository portabilityRepository, OperatorsService operatorsService) {
        this.portabilityRepository = portabilityRepository;
        this.operatorsService = operatorsService;
    }


    public void createNumberListForPortability(TicketModel ticket, List<String> numberList) {
        List<PortabilityModel> list = numberList.stream()
                .map(numero -> createNumberForPortability(numero, ticket))
                .toList();

        saveAll(list);
    }

    private void saveAll(List<PortabilityModel> listaNumeros){
        portabilityRepository.saveAll(listaNumeros);
    }

    private PortabilityModel createNumberForPortability(String numero, TicketModel ticket) {

        PortabilityModel numberForPortability = new PortabilityModel();

        BeanUtils.copyProperties(getDadosOperadora(numero), numberForPortability, "id", "solicitacaoPortabilidadeModel", "numero");

        fillDataNumber(numberForPortability, numero, ticket);

        return numberForPortability;
    }

private OperatorsModel getDadosOperadora(String numero) {
    return operatorsService.findByNumeroPortabilidade(
            numero.substring(0, 2),
            numero.substring(2, 6),
            numero.substring(6)
    );
}

    private void save(PortabilityModel model) {
        try {
            portabilityRepository.save(model);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar atualização da portabilidade", e);
        }
    }

//    public SucessResponse update(String id, UpdateNumberForPortabilityDTO dto){
//        PortabilityModel portabilityModel = findById(id);
//
//        updateNumberModelFields(portabilityModel, dto);
//        save(portabilityModel);
//
//        return new SucessResponse("Solicitacao atualizada com sucesso","OK");
//    }

//    private String gerarId() {
//    private void fillDataNumber(PortabilityModel numberForPortability, String numberToFill, RequestPortabilityModel solicitation) {
//        numberForPortability.setId(gerarId());
//        numberForPortability.setStatusSolicitacao(StatusPortability.P);
//        numberForPortability.setNumero(numberToFill);
//        numberForPortability.setRequestPortabilityModel(solicitation);
//    }

//        String prefixo = "SNUM";
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5).toUpperCase();
//        return prefixo + uuid;
//    }
//
//    private void updateNumberModelFields(PortabilityModel model, UpdateNumberForPortabilityDTO dto){
//        Optional.ofNullable(dto.status())
//                .filter(status -> !status.trim().isEmpty())
//                .ifPresent(status -> updateStatus(model,status));
//
//        Optional.ofNullable(dto.dataAgendamento())
//                .filter(data -> !data.trim().isEmpty())
//                .ifPresent(model::setDataAgendamento);
//
//        Optional.ofNullable(dto.horaAgendamento())
//                .filter(hora -> !hora.trim().isEmpty())
//                .ifPresent(model::setHoraAgendamento);
//    }
//
//    private void updateStatus(PortabilityModel model, String status) {
//        try {
//            StatusPortability statusEnum = StatusPortability.valueOf(status.trim().toUpperCase());
//            model.setStatusSolicitacao(statusEnum);
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("StatusNumber de portabilidade inválido: " + status);
//        }
//    }


}
