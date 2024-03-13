package com.oalvarez.backend.usuarios.apirest.mapper;

import com.oalvarez.backend.usuarios.apirest.dto.CreateResponseDTO;
import com.oalvarez.backend.usuarios.apirest.entity.User;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class CreateResponseMapper implements IMapper<User, CreateResponseDTO>{

    private final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @Override
    public CreateResponseDTO map(User user) {
        CreateResponseDTO createResponseDTO = new CreateResponseDTO();
        createResponseDTO.setId(user.getUuid());
        createResponseDTO.setCreated(user.getCreated().format(FORMAT));
        createResponseDTO.setModified(user.getModified().format(FORMAT));
        createResponseDTO.setLastLogin(user.getLastLogin().format(FORMAT));
        createResponseDTO.setIsactive(user.getEnabled());
        return createResponseDTO;
    }
}
