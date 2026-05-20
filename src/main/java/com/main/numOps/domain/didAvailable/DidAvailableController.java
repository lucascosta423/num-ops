package com.main.numOps.domain.didAvailable;

import com.main.numOps.domain.didAvailable.dtos.DidAvailable;
import com.main.numOps.domain.didAvailable.dtos.DidAvailableFilter;
import com.main.numOps.domain.didAvailable.dtos.DidAvailableRangeFilterDTO;
import com.main.numOps.domain.didAvailable.dtos.DidAvailableRangeUpdateRequest;
import com.main.numOps.dtos.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<ApiResponse<Page<DidAvailable>>> findAllAvailable(
            @Valid @ParameterObject DidAvailableFilter filter,
            @PageableDefault(
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {
        return ResponseEntity.ok().body(
                ApiResponse.success(
                        didAvailableService.findAllAvailable(filter, pageable),
                        "Available DIDs found successfully",
                        HttpStatus.OK.value()

                )
        );
    }

    @Operation(summary = "List all DIDs")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<DidAvailableModel>>> listAll(
            @PageableDefault(
                    sort = "id",
                    direction = Sort.Direction.ASC
            ) Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.success(
                                didAvailableService.findAll(pageable),
                                "DIDs Found",
                                HttpStatus.OK.value()
                        )
                );
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
    public ResponseEntity<ApiResponse<Integer>> updateByRange(
            @RequestBody @Valid DidAvailableRangeUpdateRequest dto
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        didAvailableService.updateByRange(dto),
                        "Updated DIDs Status",
                        HttpStatus.OK.value()
                ));
    }


    @Operation(
            summary = "Upload DIDs",
            description = """
                    The file must be a .csv or .txt file separated by semicolons.
                    
                    ### 📄 Layout do arquivo
                    
                    | Campo   | Tipo   | Obrigatório | Descrição              | Exemplo     |
                    |--------|--------|------------|------------------------|------------|
                    | cn     | String | Sim        | Codigo Nacional (DDD)  | 27         |
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
    public ResponseEntity<ApiResponse<Void>> uploadFile(
            @RequestPart("file") MultipartFile file
    ) {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                ApiResponse.success(
                        didAvailableService.uploadFile(file),
                        "Upload received. Processing in progress.",
                        HttpStatus.ACCEPTED.value()
                        )
        );
    }
}
