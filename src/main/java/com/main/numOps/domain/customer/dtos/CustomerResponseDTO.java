package com.main.numOps.domain.customer.dtos;

import com.main.numOps.domain.customer.enums.StatusCustomer;
import com.main.numOps.domain.did.enums.DidStatus;
import com.main.numOps.domain.customer.CustomerModel;
import com.main.numOps.domain.customer.enums.TypeDocument;
import com.main.numOps.domain.didAvailable.enums.DidAvailableStatus;

import java.time.LocalDateTime;

public record CustomerResponseDTO(
        Long id,
        String razao,
        String document,
        TypeDocument typeDocument,
        String cep,
        String logradouro,
        String numeroEndereco,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        String provider,
        LocalDateTime createdAt,
        StatusCustomer status
) {
    public static CustomerResponseDTO fromEntity(CustomerModel model){
        return new CustomerResponseDTO(
                model.getId(),
                model.getRazao(),
                model.getDocument(),
                model.getTypeDocument(),
                model.getCep(),
                model.getLogradouro(),
                model.getNumeroEndereco(),
                model.getComplemento(),
                model.getBairro(),
                model.getCidade(),
                model.getUf(),
                model.getProvider().getNome(),
                model.getCreatedAt(),
                model.getStatus()
        );
    }
}
