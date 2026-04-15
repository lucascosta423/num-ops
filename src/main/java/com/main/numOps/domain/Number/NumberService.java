package com.main.numOps.domain.Number;

import com.main.numOps.Enuns.StatusNumber;
import com.main.numOps.domain.Number.dtos.ActivateNumberRequest;
import com.main.numOps.domain.Number.dtos.NumberAvailableResponse;
import com.main.numOps.domain.Number.dtos.NumberResponse;
import com.main.numOps.exeptions.NotFoundException;
import com.main.numOps.utils.AuthUtils;
import com.main.numOps.utils.responseApi.SucessResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NumberService {
    private final NumberRepository numberRepository;
    private final AuthUtils authUtils;

    public NumberService(NumberRepository numberRepository, AuthUtils authUtils) {
        this.numberRepository = numberRepository;
        this.authUtils = authUtils;
    }

    public SucessResponse activateNumber(Integer id, ActivateNumberRequest dto) {

        NumberModel numberModel = findById(id);

        if (numberModel.getStatusNumber() != StatusNumber.AVAILABLE) {
            throw new IllegalStateException("Número não está disponível para ativação");
        }

        numberModel.setCliente(dto.cliente());
        numberModel.setDocumento(dto.documento());
        numberModel.setCep(dto.cep());
        numberModel.setLogradouro(dto.logradouro());
        numberModel.setNumeroEndereco(dto.numeroEndereco());
        numberModel.setComplemento(dto.complemento());
        numberModel.setBairro(dto.bairro());
        numberModel.setCidade(dto.cidade());
        numberModel.setUf(dto.uf());

        numberModel.setStatusNumber(StatusNumber.ACTIVE);
        //numberModel.setProvider(authUtils.getCurrentUser().getProvider());
        numberModel.setDataAtivacao(LocalDateTime.now());

        numberRepository.save(numberModel);

        return new SucessResponse("Solicitação de ativação criada com sucesso", "OK");
    }

    public SucessResponse cancelNumber(Integer id) {

        NumberModel numberModel = findById(id);

        if (numberModel.getStatusNumber() != StatusNumber.ACTIVE) {
            throw new IllegalStateException("Número não está ativo para cancelamento");
        }

        numberModel.setCliente(null);
        numberModel.setDocumento(null);
        numberModel.setCep(null);
        numberModel.setLogradouro(null);
        numberModel.setNumeroEndereco(null);
        numberModel.setComplemento(null);
        numberModel.setBairro(null);
        numberModel.setCidade(null);
        numberModel.setUf(null);

        numberModel.setStatusNumber(StatusNumber.AVAILABLE);
        numberModel.setProvider(null);
        numberModel.setDataAtivacao(null);

        numberRepository.save(numberModel);

        return new SucessResponse("Número cancelado com sucesso", "OK");
    }

    public NumberModel findById(Integer id) {
        return numberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Numero não encontrado"));
    }

    public Page<NumberAvailableResponse> findByNumbersAvailable(String area, String uf, Pageable pageable) {

        return numberRepository.findWithFilters(
                        StatusNumber.AVAILABLE,
                        uf,
                        area,
                        pageable)
                .map(NumberAvailableResponse::fromEntity);

    }

    public Page<NumberResponse> findAll(Pageable pageable) {
        if (authUtils.isAdmin()) {
            return numberRepository.findAll(pageable)
                    .map(NumberResponse::fromEntity);
        } else {
            return numberRepository.findByProvider(authUtils.getCurrentUser().getProvider(), pageable)
                    .map(NumberResponse::fromEntity);
        }
    }
}
