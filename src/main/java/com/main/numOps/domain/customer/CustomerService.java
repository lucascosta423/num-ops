package com.main.numOps.domain.customer;

import com.main.numOps.domain.customer.dtos.CustomerRequestDTO;
import com.main.numOps.domain.customer.dtos.CustomerResponseDTO;
import com.main.numOps.domain.customer.dtos.CustomerUpdateDTO;
import com.main.numOps.domain.providers.ProviderService;
import com.main.numOps.exeptions.NotFoundException;
import com.main.numOps.mapper.CustomerMapper;
import com.main.numOps.utils.AuthUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ProviderService providerService;
    private final AuthUtils authUtils;
    private final CustomerMapper mapper;

    public CustomerService(CustomerRepository customerRepository, ProviderService providerService, AuthUtils authUtils, CustomerMapper mapper) {
        this.customerRepository = customerRepository;
        this.providerService = providerService;
        this.authUtils = authUtils;
        this.mapper = mapper;
    }

    public void save(CustomerRequestDTO dto) {

        var customer = mapper.toModel(dto);

        var provider = authUtils.getCurrentUser().getProvider();

        customer.setProvider(provider);

        customerRepository.save(customer);

    }

    public void update(Long id, CustomerUpdateDTO dto) {

        var customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No customers found"));

        if (dto.providerId() != null) {
            customer.setProvider(
                    providerService.findById(dto.providerId())
            );
        }

        if (dto.cep() != null) {
            customer.setCep(dto.cep());
        }

        if (dto.logradouro() != null) {
            customer.setLogradouro(dto.logradouro());
        }

        if (dto.numeroEndereco() != null) {
            customer.setNumeroEndereco(dto.numeroEndereco());
        }

        if (dto.complemento() != null) {
            customer.setComplemento(dto.complemento());
        }

        if (dto.bairro() != null) {
            customer.setBairro(dto.bairro());
        }

        if (dto.cidade() != null) {
            customer.setCidade(dto.cidade());
        }

        if (dto.uf() != null) {
            customer.setUf(dto.uf());
        }

        customerRepository.save(customer);
    }

    public Page<CustomerResponseDTO> list(Pageable pageable) {

        Page<CustomerModel> customers;

        if (authUtils.isAdmin()) {
            customers = customerRepository.findAll(pageable);
        } else {
            customers = customerRepository.findByProvider(
                    authUtils.getCurrentUser().getProvider(),
                    pageable
            );
        }

        if (customers.isEmpty()) {
            throw new NotFoundException("No customers found");
        }

        return customers.map(CustomerResponseDTO::fromEntity);
    }

    public void delete(Long id){
        customerRepository.deleteById(id);
    }
}
