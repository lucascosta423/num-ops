package com.main.numOps.domain.didAvailable;

import com.main.numOps.domain.didAvailable.dtos.DidAvailable;
import com.main.numOps.domain.didAvailable.dtos.DidAvailableRangeFilterDTO;
import com.main.numOps.domain.didAvailable.dtos.DidAvailableRangeUpdateRequest;
import com.main.numOps.services.FilesUpload.NumberFilesService;
import com.main.numOps.utils.responseApi.SucessResponse;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "DIDs AVAILABLE")
@RestController
@RequestMapping("/dids")
public class DidAvailableController {

    private final DidAvailableService didAvailableService;

    public DidAvailableController(DidAvailableService didAvailableService) {
        this.didAvailableService = didAvailableService;

    }

    @Operation(summary = "List of available DIDs")
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

    @Operation(summary = "List all DIDs")
    @GetMapping
    public ResponseEntity<Page<DidAvailableModel>> listAll(Pageable pageable) {
        return ResponseEntity.ok(didAvailableService.findAll(pageable));
    }

    @Operation(summary = "Filter DIDs by range")
    @PostMapping("/filter")
    public ResponseEntity<Page<DidAvailableModel>> filter(
            @RequestBody @Valid DidAvailableRangeFilterDTO dto,
            Pageable pageable
    ) {
        return ResponseEntity.ok(didAvailableService.listByRange(dto, pageable));
    }

    @Operation(summary = "Update status in batch")
    @PatchMapping
    public ResponseEntity<Integer> updateByRange(
            @RequestBody @Valid DidAvailableRangeUpdateRequest dto
    ) {
        return ResponseEntity.ok(didAvailableService.updateByRange(dto));
    }


    @Operation(
            summary = "Upload DIDs",
            description = """
        The file must be a .csv or .txt file separated by semicolons.

        ### 📄 Layout do arquivo

        | Campo   | Tipo   | Obrigatório | Descrição              | Exemplo     |
        |--------|--------|------------|------------------------|------------|
        | cn     | String | Sim        | Número completo        | 27         |
        | prefixo| String | Sim        | Prefixo do DID         | 2888       |
        | mcdu   | String | Sim        | Código MCDU            | 0000       |
        | area   | String | Sim        | Nome da Cidade         | Serra      |
        | uf     | String | Sim        | Estado                 | ES         |
        | status | String | Não        | Status do DID          | AVAILABLE  |

        ### 🧾 Exemplo de arquivo

        ```
        cn;prefixo;mcdu;area;uf;status
        27;2888;0000;Serra;ES;AVAILABLE
        ```
        """
    )
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SucessResponse> uploadFile(
            @RequestPart("file") MultipartFile file
    ) {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(didAvailableService.uploadFile(file));
    }
}
