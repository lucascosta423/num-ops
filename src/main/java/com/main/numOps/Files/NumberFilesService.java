package com.main.numOps.Files;

import com.main.numOps.domain.didAvailable.DidAvailableModel;
import com.main.numOps.domain.didAvailable.enums.DidAvailableStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.main.numOps.utils.FileUtils.mapLineToModel;

@Slf4j
@Service
public class NumberFilesService {

    private final NumberBatchService numberBatchService;

    public NumberFilesService(NumberBatchService numberBatchService) {
        this.numberBatchService = numberBatchService;
    }

    @Async
    public void processFile(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String headerLine = reader.readLine();
            String[] headers = headerLine.split(";");

            List<DidAvailableModel> batch = new ArrayList<>();
            int batchSize = 1000;

            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {

                    DidAvailableModel numero = mapLineToModel(line, headers, DidAvailableModel::new, (model, header, value) -> {
                        switch (header) {
                            case "cn" -> model.setCn(value);
                            case "prefixo" -> model.setPrefixo(value);
                            case "mcdu" -> model.setMcdu(value);
                            case "area" -> model.setArea(value);
                            case "uf" -> model.setUfArea(value);
                        }
                        if (model.getStatus() == null) {
                            model.setStatus(DidAvailableStatus.AVAILABLE);
                        }

                    });

                    batch.add(numero);

                    if (batch.size() >= batchSize) {
                        numberBatchService.saveBatch(batch);
                        batch.clear();
                    }
                }
            }

            if (!batch.isEmpty()) {
                numberBatchService.saveBatch(batch);
            }

        } catch (IOException ex) {
            log.error("error: ", ex);
        }
    }

}
