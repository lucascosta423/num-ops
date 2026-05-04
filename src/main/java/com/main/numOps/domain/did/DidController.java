package com.main.numOps.domain.did;

import com.main.numOps.domain.did.dtos.ActivateDidRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Numero", description = "API REST para gerenciamento de numeros")
@RestController
@RequestMapping("/number")
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


}
