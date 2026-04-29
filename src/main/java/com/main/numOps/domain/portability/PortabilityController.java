package com.main.numOps.domain.portability;

import com.main.numOps.domain.portability.dto.PortabilityDTO;
import com.main.numOps.domain.portability.dto.PortabilityUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/portability")
public class PortabilityController {
    private final PortabilityService portabilityService;

    public PortabilityController(PortabilityService portabilityService) {
        this.portabilityService = portabilityService;
    }

    @GetMapping
    public ResponseEntity<Page<PortabilityDTO>> list(Pageable pageable){
        return ResponseEntity.ok().body(portabilityService.list(pageable));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody PortabilityUpdate dto) {

        int updated = portabilityService.updatePortability(dto);

        return ResponseEntity.ok(Map.of(
                "updated", updated,
                "message", "Registros atualizados com sucesso"
        ));
    }
}
