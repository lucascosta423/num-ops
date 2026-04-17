package com.main.numOps.domain.operators;


import com.main.numOps.domain.operators.dtos.CarrierResponse;
import com.main.numOps.exeptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OperatorsService {

    private final OperatorsRepository operatorsRepository;

    public OperatorsService(OperatorsRepository operatorsRepository) {
        this.operatorsRepository = operatorsRepository;
    }


    public Page<CarrierResponse> findAll(Pageable pageable) {
        return operatorsRepository.findAll(pageable)
                .map(CarrierResponse::fromEntity);
    }

    public CarrierResponse findByNumber(String prefixo, String mcdu, String codigoNacional){
        return operatorsRepository.findByNumero(prefixo,mcdu,codigoNacional)
                .map(CarrierResponse::fromEntity)
                .orElseThrow(() -> new NotFoundException("Numero nao encontrado"));
    }
}
