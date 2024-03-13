package com.oalvarez.backend.usuarios.apirest.mapper;

import com.oalvarez.backend.usuarios.apirest.dto.PhoneDTO;
import com.oalvarez.backend.usuarios.apirest.dto.UserDTO;
import com.oalvarez.backend.usuarios.apirest.entity.Phone;
import com.oalvarez.backend.usuarios.apirest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRequestMapper implements IMapper<UserDTO, User> {

    private final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User map(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setUuid(userDTO.getUuid());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setPhones(getPhones(userDTO.getPhones()));
        user.setEnabled(userDTO.isIsactive());
        user.setLastLogin((userDTO.getLastLogin() == null) ? null : LocalDate.parse(userDTO.getLastLogin(), FORMAT));
        user.setModified((userDTO.getModified() == null) ? null : LocalDate.parse(userDTO.getModified(), FORMAT));
        user.setCreated((userDTO.getCreated() == null) ? null : LocalDate.parse(userDTO.getCreated(), FORMAT));
        return user;
    }

    private List<Phone> getPhones(List<PhoneDTO> phonesDTO){
        return phonesDTO.stream()
                .map(this::getPhone)
                .collect(Collectors.toList());
    }

    private Phone getPhone(PhoneDTO phoneDTO){
        Phone phone = new Phone();
        phone.setNumber(Long.parseLong(phoneDTO.getNumber()));
        phone.setCityCode(phoneDTO.getCityCode());
        phone.setCountryCode(phoneDTO.getCountryCode());
        return phone;
    }
}