package com.main.numOps.domain.didAvailable;

import com.main.numOps.domain.didAvailable.dtos.DidAvailable;
import com.main.numOps.domain.didAvailable.dtos.DidAvailableRangeFilterDTO;
import com.main.numOps.domain.didAvailable.dtos.DidAvailableRangeUpdateRequest;
import com.main.numOps.services.FilesUpload.NumberFilesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "DIDs Available", description = "Gerenciamento de DIDs disponíveis")
@RestController
@RequestMapping("/dids")
public class DidAvailableController {

    private final DidAvailableService didAvailableService;
    private final NumberFilesService numberFilesService;

    public DidAvailableController(DidAvailableService didAvailableService, NumberFilesService numberFilesService) {
        this.didAvailableService = didAvailableService;
        this.numberFilesService = numberFilesService;
    }

    @Operation(summary = "Lista DIDs disponíveis")
    @GetMapping("/available")
    public ResponseEntity<Page<DidAvailable>> listByNumbersAvailable(
            @Parameter(description = "DDD", example = "27")
            @RequestParam String area,

            @Parameter(description = "UF", example = "ES")
            @RequestParam String uf,

            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(
                didAvailableService.findByNumbersAvailable(area, uf, pageable)
        );
    }

    @Operation(summary = "Lista todos os DIDs")
    @GetMapping
    public ResponseEntity<Page<DidAvailableModel>> listAll(Pageable pageable) {
        return ResponseEntity.ok(didAvailableService.findAll(pageable));
    }

    @Operation(summary = "Filtrar DIDs por range")
    @PostMapping("/filter")
    public ResponseEntity<Page<DidAvailableModel>> filter(
            @RequestBody @Valid DidAvailableRangeFilterDTO dto,
            Pageable pageable
    ) {
        return ResponseEntity.ok(didAvailableService.listByRange(dto, pageable));
    }

    @Operation(summary = "Atualizar status em lote")
    @PatchMapping
    public ResponseEntity<Integer> updateByRange(
            @RequestBody @Valid DidAvailableRangeUpdateRequest dto
    ) {
        return ResponseEntity.ok(didAvailableService.updateByRange(dto));
    }

    @Operation(summary = "Upload de DIDs")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(
            @RequestPart("file") MultipartFile file
    ) throws IOException {

        numberFilesService.processFile(file);
        return ResponseEntity.ok("Arquivo processado com sucesso");
    }
}
