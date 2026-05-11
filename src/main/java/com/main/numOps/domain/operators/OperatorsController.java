package com.main.numOps.domain.operators;

import com.main.numOps.domain.operators.dtos.NumberLookupDTO;
import com.main.numOps.domain.operators.dtos.CarrierResponse;
import com.main.numOps.Files.OperatorsFilesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "OPERATORS")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/operadoras")
public class OperatorsController {

    private final OperatorsFilesService operatorsFilesService;
    private final OperatorsService operatorsService;

    public OperatorsController(OperatorsFilesService operatorsFilesService, OperatorsService operatorsService) {
        this.operatorsFilesService = operatorsFilesService;
        this.operatorsService = operatorsService;
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {

            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty.");
            }

            operatorsFilesService.processFile(file);

            return ResponseEntity.ok("File uploaded and processed successfully.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Page<CarrierResponse>> findAll(@PageableDefault(page = 0, size = 20,direction = Sort.Direction.ASC)Pageable pageable) {
        Page<CarrierResponse> pageResult = operatorsService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(pageResult);
    }

    @GetMapping("/numero")
    public ResponseEntity<CarrierResponse> getByNumero(@RequestBody NumberLookupDTO numeroOperadoraDTO){

        var operadoraDTO = operatorsService.findByNumber(
                numeroOperadoraDTO.prefixo(),
                numeroOperadoraDTO.mcdu(),
                numeroOperadoraDTO.codigoNacional()
        );

        return ResponseEntity.status(HttpStatus.OK).body(operadoraDTO);
    }

}
