package com.main.numOps.domain.Number;

import com.main.numOps.domain.Number.dtos.ActivateNumberRequest;
import com.main.numOps.domain.Number.dtos.NumberAvailableResponse;
import com.main.numOps.services.FilesUpload.NumberFilesService;
import com.main.numOps.utils.responseApi.SucessResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/numero")
public class NumberController {
    private final NumberService numberService;
    private final NumberFilesService numberFilesService;

    public NumberController(NumberService numberService, NumberFilesService numberFilesService) {
        this.numberService = numberService;
        this.numberFilesService = numberFilesService;
    }


    @GetMapping("list")
    public ResponseEntity<Page<NumberAvailableResponse>> listByNumbersAvailable(
            @RequestParam(value = "area") String area,
            @RequestParam(value = "uf") String uf,
            @PageableDefault(page = 0,size = 10,direction = Sort.Direction.ASC) Pageable pageable){

        return ResponseEntity.status(HttpStatus.OK).body(numberService.findByNumbersAvailable(area,uf,pageable));
    }

    @PostMapping("activate/{id}")
    public ResponseEntity<SucessResponse> requestNumberActivate(@PathVariable Integer id, @RequestBody @Valid ActivateNumberRequest dto) {

        var success = numberService.activateNumber(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(success);
    }

    @PostMapping("cancel/{id}")
    public ResponseEntity<SucessResponse> activateNumber(@PathVariable Integer id) {

        var success = numberService.cancelNumber(id);

        return ResponseEntity.status(HttpStatus.OK).body(success);
    }

    @Hidden
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
