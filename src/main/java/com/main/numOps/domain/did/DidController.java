package com.main.numOps.domain.did;

import com.main.numOps.domain.did.dtos.ActivateDidRequest;
import com.main.numOps.domain.did.dtos.DidWithoutDidDTO;
import com.main.numOps.domain.didAvailable.DidAvailableModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "DIDs CONFIGURED")
@RestController
@RequestMapping("/did")
public class DidController {
    private final DidService didService;

    public DidController(DidService didService ) {
        this.didService = didService;
    }

    @Operation(summary = "Ativar números (DIDs)")
    @PostMapping("/activate")
    @ResponseStatus(HttpStatus.CREATED)
    public List<DidModel> activate(@RequestBody @Valid ActivateDidRequest request) {
        return didService.save(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<DidWithoutDidDTO> listAll(@PageableDefault()Pageable pageable){
        return didService.findAll(pageable);
    }

    @GetMapping("/{document}")
    @ResponseStatus(HttpStatus.OK)
    public Page<DidAvailableModel> listBydocument(@PathVariable @Valid String document, Pageable pageable){
        return didService.findByDiddocument(document,pageable);
    }
}
