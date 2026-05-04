package com.main.numOps.domain.did;

import com.main.numOps.Enuns.DidStatus;
import com.main.numOps.domain.did.dtos.ActivateDidRequest;
import com.main.numOps.domain.didAvailable.DidAvailableService;
import com.main.numOps.exeptions.NotFoundException;
import com.main.numOps.mapper.DidMapper;
import com.main.numOps.utils.AuthUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DidService {
    private final DidRepository didRepository;
    private final DidAvailableService didAvailableService;
    private final DidMapper mapper;
    private final AuthUtils authUtils;

    public DidService(DidRepository didRepository, DidAvailableService didAvailableService, DidMapper mapper, AuthUtils authUtils) {
        this.didRepository = didRepository;
        this.didAvailableService = didAvailableService;
        this.mapper = mapper;
        this.authUtils = authUtils;
    }

    @Transactional
    public List<DidModel> save(ActivateDidRequest didRequest) {

        var listDid = new ArrayList<DidModel>();

        for (Integer idDid : didRequest.numeros()) {

            var didModel = mapper.toModelList(didRequest);

            didModel.setProvider(authUtils.getCurrentUser().getProvider());

            var did = didAvailableService.findById(idDid);

            didModel.setDid(did);

            listDid.add(didModel);
        }

        var reult =  didRepository.saveAll(listDid);

        didAvailableService.updateStatus(didRequest.numeros(), DidStatus.UNAVAILABLE);

        return reult;
    }

    public DidModel findById(Integer id) {
        return didRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Numero não encontrado"));
    }

//    public Page<NumberResponse> findAll(Pageable pageable) {
//        if (authUtils.isAdmin()) {
//            return didRepository.findAll(pageable)
//                    .map(NumberResponse::fromEntity);
//        } else {
//            return didRepository.findByProvider(authUtils.getCurrentUser().getProvider(), pageable)
//                    .map(NumberResponse::fromEntity);
//        }
//    }
}
