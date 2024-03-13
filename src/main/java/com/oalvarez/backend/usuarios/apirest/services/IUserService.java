package com.oalvarez.backend.usuarios.apirest.services;

import com.oalvarez.backend.usuarios.apirest.dto.CreateResponseDTO;
import com.oalvarez.backend.usuarios.apirest.dto.UserDTO;
import com.oalvarez.backend.usuarios.apirest.dto.UserResponseDTO;

import java.util.List;

public interface IUserService {

    public List<UserResponseDTO> findAll();

    public UserResponseDTO findById(Long id);

    public CreateResponseDTO create(UserDTO user);

    public UserResponseDTO update(UserDTO user);

    public UserResponseDTO findByUuid(String uuid);

    public UserDTO findByUuidUpdate(String uuid);

    public boolean existsByEmail(String email);

}