package com.main.numOps.domain.did;

import com.main.numOps.Enuns.StatusNumber;
import com.main.numOps.domain.did.dtos.NumberAvailableResponse;
import com.main.numOps.domain.did.dtos.NumberResponse;
import com.main.numOps.exeptions.NotFoundException;
import com.main.numOps.utils.AuthUtils;
import com.main.numOps.utils.responseApi.SucessResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DidService {
    private final DidRepository didRepository;
    private final AuthUtils authUtils;

    public DidService(DidRepository didRepository, AuthUtils authUtils) {
        this.didRepository = didRepository;
        this.authUtils = authUtils;
    }

    public SucessResponse activateNumber(Integer id) {

        DidModel didModel = findById(id);

        if (didModel.getStatusNumber() != StatusNumber.AVAILABLE) {
            throw new IllegalStateException("Número não está disponível para ativação");
        }

        didModel.setStatusNumber(StatusNumber.ACTIVE);

        didRepository.save(didModel);

        return new SucessResponse("Solicitação de ativação criada com sucesso", "OK");
    }

    public SucessResponse cancelNumber(Integer id) {

        DidModel didModel = findById(id);

        if (didModel.getStatusNumber() != StatusNumber.ACTIVE) {
            throw new IllegalStateException("Número não está ativo para cancelamento");
        }

        didModel.setStatusNumber(StatusNumber.AVAILABLE);

        didRepository.save(didModel);

        return new SucessResponse("Número cancelado com sucesso", "OK");
    }

    public DidModel findById(Integer id) {
        return didRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Numero não encontrado"));
    }

    public Page<NumberAvailableResponse> findByNumbersAvailable(String area, String uf, Pageable pageable) {

        return didRepository.findWithFilters(
                        StatusNumber.AVAILABLE,
                        uf,
                        area,
                        pageable)
                .map(NumberAvailableResponse::fromEntity);

    }

    public Page<NumberResponse> findAll(Pageable pageable) {
        if (authUtils.isAdmin()) {
            return didRepository.findAll(pageable)
                    .map(NumberResponse::fromEntity);
        } else {
            return didRepository.findByProvider(authUtils.getCurrentUser().getProvider(), pageable)
                    .map(NumberResponse::fromEntity);
        }
    }
}
