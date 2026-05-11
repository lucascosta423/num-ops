package com.main.numOps.mapper;

import com.main.numOps.domain.customer.CustomerModel;
import com.main.numOps.domain.customer.dtos.CustomerRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerModel toModel(CustomerRequestDTO dto){
        var customer = new CustomerModel();

        customer.setRazao(dto.razao());
        customer.setDocument(dto.document());
        customer.setCep(dto.cep());
        customer.setLogradouro(dto.logradouro());
        customer.setNumeroEndereco(dto.numeroEndereco());
        customer.setComplemento(dto.complemento());
        customer.setBairro(dto.bairro());
        customer.setCidade(dto.cidade());
        customer.setUf(dto.uf());
        customer.setTypeDocument(dto.typeDocument());

        return customer;
    }
}
