package com.oalvarez.backend.usuarios.apirest.mapper;

import com.oalvarez.backend.usuarios.apirest.dto.PhoneDTO;
import com.oalvarez.backend.usuarios.apirest.dto.UserResponseDTO;
import com.oalvarez.backend.usuarios.apirest.entity.Phone;
import com.oalvarez.backend.usuarios.apirest.entity.User;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsersResponseMapper implements IMapper<List<User>, List<UserResponseDTO>>{

    private final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public List<UserResponseDTO> map(List<User> users) {
        return getUsers(users);
    }

    private List<UserResponseDTO> getUsers(List<User> users){
        return users.stream()
                .map(this::getUser)
                .collect(Collectors.toList());
    }

    private UserResponseDTO getUser(User user){
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(user.getUuid());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhones(getPhones(user.getPhones()));
        userDTO.setCreated(user.getCreated().format(FORMAT));
        userDTO.setModified(user.getModified().format(FORMAT));
        userDTO.setLastLogin(user.getLastLogin().format(FORMAT));
        userDTO.setIsactive(user.getEnabled());
        return userDTO;
    }

    private List<PhoneDTO> getPhones(List<Phone> phones){
        return phones.stream()
                .map(this::getPhone)
                .collect(Collectors.toList());
    }

    private PhoneDTO getPhone(Phone phone){
        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setNumber(String.valueOf(phone.getNumber()));
        phoneDTO.setCityCode(phone.getCityCode());
        phoneDTO.setCountryCode(phone.getCountryCode());
        return phoneDTO;
    }
}