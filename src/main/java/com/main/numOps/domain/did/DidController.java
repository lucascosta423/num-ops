package com.main.numOps.domain.did;

import com.main.numOps.domain.did.dtos.ActivateDidRequest;
import com.main.numOps.domain.did.dtos.DidWithoutDidDTO;
import com.main.numOps.domain.didAvailable.DidAvailableModel;
import com.main.numOps.dtos.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "DIDs CONFIGURED")
@RestController
@RequestMapping("/did")
public class DidController {
    private final DidService didService;

    public DidController(DidService didService) {
        this.didService = didService;
    }

    @Operation(summary = "Request activation of dids")
    @PostMapping("/activate")
    @ResponseStatus(HttpStatus.CREATED)
    public List<DidModel> activate(@RequestBody @Valid ActivateDidRequest request) {
        return didService.save(request);
    }

    @GetMapping
    @Operation(summary = "List from dids configured")
    public ResponseEntity<ApiResponse<Page<DidWithoutDidDTO>>> listAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                                didService.findAll(pageable),
                                "Dids Configured found",
                                HttpStatus.OK.value()
                        )
                );
    }

}
