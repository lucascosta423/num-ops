package com.main.numOps.services.FilesUpload;

import com.main.numOps.domain.did.DidModel;
import com.main.numOps.domain.did.DidRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NumberBatchService {

    private final DidRepository didRepository;

    public NumberBatchService(DidRepository didRepository) {
        this.didRepository = didRepository;
    }

    @Transactional
    public void saveBatch(List<DidModel> batch) {
        didRepository.saveAll(batch);
        didRepository.flush();
    }
}