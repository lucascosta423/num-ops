package com.main.numOps.domain.didAvailable;

import com.main.numOps.Enuns.DidStatus;
import com.main.numOps.domain.didAvailable.dtos.DidAvailable;
import com.main.numOps.exeptions.NotFoundException;
import com.main.numOps.utils.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DidAvailableService {
    private final DidAvailableRepository didAvailableRepository;

    public DidAvailableService(DidAvailableRepository didAvailableRepository) {
        this.didAvailableRepository = didAvailableRepository;
    }


    public DidAvailableModel findById(Integer id) {
        return didAvailableRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Numero não encontrado"));
    }

    public Page<DidAvailable> findByNumbersAvailable(String area, String uf, Pageable pageable) {
        return didAvailableRepository.findWithFilters(
                        DidStatus.AVAILABLE,
                        uf,
                        area,
                        pageable)
                .map(DidAvailable::fromEntity);
    }

    public int updateStatus(List<Integer> ids, DidStatus status){
        return didAvailableRepository.updateStatusDidAvailable(
                ids,
                status,
                DateUtils.nowWithoutNanos()
        );
    }

    public Page<DidAvailableModel> findAll(Pageable pageable) {
        return didAvailableRepository.findAll(pageable);
    }
}
