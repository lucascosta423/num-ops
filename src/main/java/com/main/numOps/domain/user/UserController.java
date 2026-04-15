package com.main.numOps.domain.user;

import com.main.numOps.dtos.user.RequestSaveUsuarioDTO;
import com.main.numOps.dtos.user.RequestUpdateUsuarioDTO;
import com.main.numOps.dtos.user.ResponseUsuarioDto;
import com.main.numOps.domain.providers.ProviderService;
import com.main.numOps.utils.responseApi.SucessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Usuario", description = "Gerenciamento de usuarios")
@RestController
@RequestMapping("/usuario")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService, ProviderService providerService) {
        this.userService = userService;
    }

    @PostMapping("/save/user")
    public ResponseEntity<SucessResponse> saveUsuario(@RequestBody @Valid RequestSaveUsuarioDTO requestSaveUsuarioDTO){

        var sucess = userService.save(requestSaveUsuarioDTO);

       return ResponseEntity.status(HttpStatus.CREATED).body(sucess);
    }

    @GetMapping("/listAll")
    public ResponseEntity<Page<ResponseUsuarioDto>> getAllUsuarios(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    direction = Sort.Direction.ASC)
            Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAllUsers(pageable));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<SucessResponse> updateUser(@PathVariable(value = "id") UUID id,
                                                     @Valid @RequestBody RequestUpdateUsuarioDTO usuarioDto){

        SucessResponse response = userService.updateUser(id,usuarioDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<SucessResponse> changeProviderStatus(@PathVariable(value = "id") UUID id){
        SucessResponse response = userService.changeUserStatus(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
