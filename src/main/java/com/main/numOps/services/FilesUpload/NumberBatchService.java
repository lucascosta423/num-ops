package com.main.numOps.services.FilesUpload;

import com.main.numOps.domain.didAvailable.DidAvailableModel;
import com.main.numOps.domain.didAvailable.DidAvailableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NumberBatchService {

    private final DidAvailableRepository didRepository;

    public NumberBatchService(DidAvailableRepository didRepository) {
        this.didRepository = didRepository;
    }

    @Transactional
    public void saveBatch(List<DidAvailableModel> batch) {
        didRepository.saveAll(batch);
        didRepository.flush();
    }
}