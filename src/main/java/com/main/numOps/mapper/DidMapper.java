package com.main.numOps.mapper;

import com.main.numOps.Enuns.DidStatus;
import com.main.numOps.domain.did.DidModel;
import com.main.numOps.domain.did.dtos.ActivateDidRequest;
import org.springframework.stereotype.Component;

@Component
public class DidMapper {

    public DidModel toModelList(ActivateDidRequest didRequest) {
        var did = new DidModel();

        did.setRazao(didRequest.razao());
        did.setDocumento(didRequest.documento());
        did.setCep(didRequest.cep());
        did.setLogradouro(didRequest.logradouro());
        did.setNumeroEndereco(didRequest.numeroEndereco());
        did.setComplemento(didRequest.complemento());
        did.setBairro(didRequest.bairro());
        did.setCidade(didRequest.cidade());
        did.setUf(didRequest.uf());
        did.setStatus(DidStatus.ACTIVE);

        return did;
    }
}
