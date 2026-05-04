package com.main.numOps.domain.didAvailable;

import com.main.numOps.domain.didAvailable.dtos.DidAvailable;
import com.main.numOps.services.FilesUpload.NumberFilesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Numero", description = "API REST para gerenciamento de numeros")
@RestController
@RequestMapping("/did")
public class DidAvailableController {
    private final DidAvailableService didAvailableService;
    private final NumberFilesService numberFilesService;

    public DidAvailableController(DidAvailableService didAvailableService, NumberFilesService numberFilesService) {
        this.didAvailableService = didAvailableService;
        this.numberFilesService = numberFilesService;
    }

    @GetMapping("available")
    public ResponseEntity<Page<DidAvailable>> listByNumbersAvailable(
            @RequestParam(value = "area") String area,
            @RequestParam(value = "uf") String uf,
            @PageableDefault(page = 0,size = 10,direction = Sort.Direction.ASC) Pageable pageable){

        return ResponseEntity.status(HttpStatus.OK).body(didAvailableService.findByNumbersAvailable(area,uf,pageable));
    }

    @GetMapping("list")
    public ResponseEntity<Page<DidAvailableModel>> listAll(@PageableDefault(page = 0,size = 10,direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK)
                .body(didAvailableService.findAll(pageable));
    }

    @PostMapping("upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            numberFilesService.processFile(file);
            return ResponseEntity.ok("File uploaded and processed successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file.");
        }
    }
}
