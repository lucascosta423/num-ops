package com.main.numOps.domain.user;

import com.main.numOps.Enuns.StatusNumber;
import com.main.numOps.Enuns.UserRole;
import com.main.numOps.dtos.user.RequestSaveUsuarioDTO;
import com.main.numOps.dtos.user.RequestUpdateUsuarioDTO;
import com.main.numOps.dtos.user.ResponseUsuarioDto;
import com.main.numOps.exeptions.NotFoundException;
import com.main.numOps.domain.providers.ProviderService;
import com.main.numOps.utils.responseApi.SucessResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private  final ProviderService providerService;

    public UserService(UserRepository userRepository, ProviderService providerService) {
        this.userRepository = userRepository;
        this.providerService = providerService;
    }

    @Transactional
    public SucessResponse save(RequestSaveUsuarioDTO dto){

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(dto, userModel);

        String tipo = dto.role().toLowerCase();

        if (!tipo.equals("admin") && !tipo.equals("user")) {
            throw new IllegalArgumentException("Tipo de usuário inválido: " + dto.role());
        }

        fillDataUser(userModel, dto);
        userRepository.save(userModel);

        String msg = tipo.equals("admin") ?
                "Administrador criado com sucesso" :
                "Usuário criado com sucesso";

        return new SucessResponse(msg, "OK");
    }

    public Page<ResponseUsuarioDto> findAllUsers(Pageable pageable) {

        return userRepository.findAll(pageable)
                .map(ResponseUsuarioDto::fromEntity);
    }

    public UserModel findByIdUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario não encontrado"));
    }

    @Transactional
    public SucessResponse updateUser(UUID id, RequestUpdateUsuarioDTO dto){

        UserModel usuario = findByIdUser(id);
        updateDataUser(usuario, dto);
        userRepository.save(usuario);

        return new SucessResponse("Usuario atualizado","Ok");
    }

    private void fillDataUser(UserModel userModel,RequestSaveUsuarioDTO usuarioDTO){
        userModel.setStatusNumber(StatusNumber.AVAILABLE);
        userModel.setPassword(cryptPassword(usuarioDTO.senha()));
        userModel.setRole(UserRole.valueOf(usuarioDTO.role().toUpperCase()));
        userModel.setProvider(providerService.findById(usuarioDTO.provedor()));

    }

    private void updateDataUser(UserModel userModel, RequestUpdateUsuarioDTO dto){

        Optional.ofNullable(dto.getNome())
                .filter(nome -> !nome.trim().isEmpty())
                .ifPresent(userModel::setName);

        Optional.ofNullable(dto.getEmail())
                .filter(email -> !email.trim().isEmpty())
                .ifPresent(userModel::setEmail);

        Optional.ofNullable(dto.getSenha())
                .filter(senha -> !senha.trim().isEmpty())
                .ifPresent(senha -> userModel.setPassword(cryptPassword(senha)));
    }

    private String cryptPassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }

    @Transactional
    public SucessResponse changeUserStatus(UUID id){
        var usuario = findByIdUser(id);

        switch (usuario.getStatusNumber()){
            case ACTIVE:
                usuario.setStatusNumber(StatusNumber.INACTIVE);
                return new SucessResponse("Usuario desativado com sucesso","OK");
            case INACTIVE:
                usuario.setStatusNumber(StatusNumber.ACTIVE);
                return new SucessResponse("Usuario ativado com sucesso","OK");
        }

        userRepository.save(usuario);

        return new SucessResponse("Nao foi possivel alterar o statusNumber do usuario","Erro");
    }
}
