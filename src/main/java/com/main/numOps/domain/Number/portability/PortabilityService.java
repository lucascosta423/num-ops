package com.main.numOps.domain.Number.portability;

import com.main.numOps.domain.Number.portability.dto.PortabilityDTO;
import com.main.numOps.domain.Number.portability.dto.PortabilityUpdate;
import com.main.numOps.domain.ticket.TicketModel;
import com.main.numOps.mapper.PortabilityMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PortabilityService {
    private final PortabilityRepository portabilityRepository;
    private final PortabilityMapper portabilityMapper;

    public PortabilityService(PortabilityRepository portabilityRepository, PortabilityMapper portabilityMapper) {
        this.portabilityRepository = portabilityRepository;
        this.portabilityMapper = portabilityMapper;
    }

    public Page<PortabilityDTO> list(Pageable pageable){
        return portabilityRepository.findAll(pageable)
                .map(PortabilityDTO::fromEntity);
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

    public void deleteByTicket(TicketModel ticketModel){
        portabilityRepository.deleteByTicket(ticketModel);
    }


    private void save(PortabilityModel model) {
        try {
            portabilityRepository.save(model);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar atualização da portabilidade", e);
        }
    }

    @Transactional
    public int updatePortability(PortabilityUpdate dto) {

        if (dto.numerosId() == null || dto.numerosId().isEmpty()) {
            throw new IllegalArgumentException("Lista de IDs não pode ser vazia");
        }

        return portabilityRepository.updatePortability(
                dto.numerosId(),
                dto.dataAgendamento(),
                dto.horaAgendamento(),
                dto.status()
        );
    }



}
