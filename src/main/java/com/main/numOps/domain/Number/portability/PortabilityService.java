package com.main.numOps.domain.Number.portability;

import com.main.numOps.domain.ticket.TicketModel;
import com.main.numOps.mapper.PortabilityMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortabilityService {
    private final PortabilityRepository portabilityRepository;
    private final PortabilityMapper portabilityMapper;

    public PortabilityService(PortabilityRepository portabilityRepository, PortabilityMapper portabilityMapper) {
        this.portabilityRepository = portabilityRepository;
        this.portabilityMapper = portabilityMapper;
    }


    public void createPortabilityNumbers(TicketModel ticket, List<String> numberList) {
        List<PortabilityModel> list = numberList.stream()
                .map(numero -> buildPortabilityNumber(numero, ticket))
                .toList();

        saveAll(list);
    }

    private PortabilityModel buildPortabilityNumber(String numero, TicketModel ticket) {
        return portabilityMapper.toModel(numero,ticket);
    }

    private void saveAll(List<PortabilityModel> listaNumeros){
        portabilityRepository.saveAll(listaNumeros);
    }

    public void cancelByTicket(TicketModel ticketModel){
        portabilityRepository.cancelByTicket(ticketModel);
    }


    private void save(PortabilityModel model) {
        try {
            portabilityRepository.save(model);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar atualização da portabilidade", e);
        }
    }



}
