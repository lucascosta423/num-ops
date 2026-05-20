package com.main.numOps.domain.customer;

import com.main.numOps.domain.customer.dtos.CustomerRequestDTO;
import com.main.numOps.domain.customer.dtos.CustomerResponseDTO;
import com.main.numOps.domain.customer.dtos.CustomerUpdateDTO;
import com.main.numOps.dtos.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CUSTOMER")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Created new customer")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> created(@Valid @RequestBody CustomerRequestDTO customerDTO){

        customerService.save(customerDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                      null,
                        "Customer created sucessfull",
                        HttpStatus.CREATED.value()
                ));
    }

    @Operation(summary = "List all customer")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CustomerResponseDTO>>> list(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        customerService.list(pageable),
                        "Customers Found",
                        HttpStatus.OK.value()
                ));
    }

    @Operation(summary = "Update data customer")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(
            @PathVariable Long id,
            @RequestBody CustomerUpdateDTO dto
    ) {

        customerService.update(id, dto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success(
                        null,
                        "Customer update sucessfull",
                        HttpStatus.NO_CONTENT.value()
                ));
    }
}
