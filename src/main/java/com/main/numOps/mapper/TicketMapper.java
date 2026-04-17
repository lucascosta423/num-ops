package com.main.numOps.mapper;

import com.main.numOps.domain.ticket.TicketModel;
import com.main.numOps.domain.ticket.dtos.TicketRequest;
import com.main.numOps.utils.AuthUtils;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    private final AuthUtils authUtils;

    public TicketMapper(AuthUtils authUtils) {
        this.authUtils = authUtils;
    }

    public TicketModel toModel(TicketRequest request) {
        TicketModel model = new TicketModel();

        model.setRazao(request.razao());
        model.setDocumento(request.documento());
        model.setCep(request.cep());
        model.setLogradouro(request.logradouro());
        model.setNumeroEndereco(request.numeroEndereco());
        model.setComplemento(request.complemento());
        model.setBairro(request.bairro());
        model.setCidade(request.cidade());
        model.setUf(request.uf());
        model.setProvider(authUtils.getCurrentUser().getProvider());
        model.setUser(authUtils.getCurrentUser());

        return model;
    }
}
