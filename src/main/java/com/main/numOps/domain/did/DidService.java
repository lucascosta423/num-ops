package com.main.numOps.domain.did;

import com.main.numOps.domain.customer.CustomerModel;
import com.main.numOps.domain.customer.CustomerService;
import com.main.numOps.domain.did.dtos.ActivateDidRequest;
import com.main.numOps.domain.did.dtos.DidWithoutDidDTO;
import com.main.numOps.domain.did.enums.DidStatus;
import com.main.numOps.domain.didAvailable.DidAvailableModel;
import com.main.numOps.domain.didAvailable.DidAvailableService;
import com.main.numOps.domain.didAvailable.enums.DidAvailableStatus;
import com.main.numOps.exeptions.NotFoundException;
import com.main.numOps.utils.AuthUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DidService {
    private final DidRepository didRepository;
    private final DidAvailableService didAvailableService;
    private final AuthUtils authUtils;
    private final CustomerService customerService;

    public DidService(DidRepository didRepository, DidAvailableService didAvailableService, AuthUtils authUtils, CustomerService customerService) {
        this.didRepository = didRepository;
        this.didAvailableService = didAvailableService;
        this.authUtils = authUtils;
        this.customerService = customerService;
    }

    @Transactional
    public List<DidModel> save(ActivateDidRequest didRequest) {

        CustomerModel customer = customerService.findById(didRequest.idCustomer());

        Map<Long, DidAvailableModel> didMap = didAvailableService
                .findAllByIds(didRequest.numeros())
                .stream()
                .collect(Collectors.toMap(DidAvailableModel::getId, Function.identity()));

        if (didMap.size() != didRequest.numeros().size()) {
            List<Long> notFound = didRequest.numeros().stream()
                    .filter(id -> !didMap.containsKey(id))
                    .toList();
            throw new NotFoundException("DIDs not found: " + notFound);
        }

        List<DidModel> listDid = didRequest.numeros().stream()
                .map(idDid -> {
                    DidModel didModel = new DidModel();
                    didModel.setProvider(authUtils.getCurrentUser().getProvider());
                    didModel.setCustomer(customer);
                    didModel.setDid(didMap.get(idDid));
                    didModel.setStatus(DidStatus.ACTIVE);
                    return didModel;
                })
                .toList();

        didAvailableService.updateStatus(didRequest.numeros(), DidAvailableStatus.UNAVAILABLE);

        return didRepository.saveAll(listDid);
    }

    public DidModel findById(Long id) {
        return didRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Did Not Found"));
    }

    public Page<DidWithoutDidDTO> findAll(Pageable pageable) {

        if (authUtils.isAdmin()) {
            return didRepository.findAll(pageable)
                    .map(DidWithoutDidDTO::fromEntity);
        } else {
            return didRepository.findByProvider(authUtils.getCurrentUser().getProvider(),pageable)
                    .map(DidWithoutDidDTO::fromEntity);
        }
    }

    public Page<DidWithoutDidDTO> findByCustomer(Long idCustomer,Pageable pageable) {

        var customer = customerService.findById(idCustomer);

        return didRepository.findByCustomer(customer,pageable)
                    .map(DidWithoutDidDTO::fromEntity);

    }

}
