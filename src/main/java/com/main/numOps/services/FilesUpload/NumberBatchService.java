package com.main.numOps.services.FilesUpload;

import com.main.numOps.domain.Number.did.NumberModel;
import com.main.numOps.domain.Number.did.NumberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NumberBatchService {

    private final NumberRepository numberRepository;

    public NumberBatchService(NumberRepository numberRepository) {
        this.numberRepository = numberRepository;
    }

    @Transactional
    public void saveBatch(List<NumberModel> batch) {
        numberRepository.saveAll(batch);
        numberRepository.flush();
    }
}