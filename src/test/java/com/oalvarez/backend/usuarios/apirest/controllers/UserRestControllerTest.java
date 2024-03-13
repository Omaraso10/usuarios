package com.oalvarez.backend.usuarios.apirest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oalvarez.backend.usuarios.apirest.dto.CreateResponseDTO;
import com.oalvarez.backend.usuarios.apirest.dto.UserDTO;
import com.oalvarez.backend.usuarios.apirest.dto.UserResponseDTO;
import com.oalvarez.backend.usuarios.apirest.services.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class UserRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IUserService userService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        this.mockMvc = MockMvcBuilders.standaloneSetup(new UserRestController(userService)).build();
    }

    @Test
    public void obtieneUsuariosTest() throws Exception {
        List<UserResponseDTO> users = new ArrayList<>();
        UserResponseDTO user1 = new UserResponseDTO();
        user1.setId("1");
        user1.setName("John Doe");
        user1.setEmail("john@example.com");
        user1.setPhones(null);
        user1.setCreated("2020-01-01");
        user1.setModified("2020-01-01");
        user1.setLastLogin("2020-01-01");
        user1.setIsactive(true);
        UserResponseDTO user2 = new UserResponseDTO();
        user2.setId("2");
        user2.setName("Jane Doe");
        user2.setEmail("john@example.com");
        user2.setPhones(null);
        user2.setCreated("2020-01-01");
        user2.setModified("2020-01-01");
        user2.setLastLogin("2020-01-01");
        user2.setIsactive(true);
        users.add(user1);
        users.add(user2);

        given(userService.findAll()).willReturn(users);

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuarios").isArray())
                .andExpect(jsonPath("$.usuarios[0].name").value("John Doe"))
                .andExpect(jsonPath("$.usuarios[1].name").value("Jane Doe"));
    }

    @Test
    public void testShowUsuarioExistente() throws Exception {
        String uuid = "uuid-test";
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId("1");
        userResponseDTO.setName("John Doe");
        userResponseDTO.setEmail("john@example.com");
        userResponseDTO.setPhones(null);
        userResponseDTO.setCreated("2020-01-01");
        userResponseDTO.setModified("2020-01-01");
        userResponseDTO.setLastLogin("2020-01-01");
        userResponseDTO.setIsactive(true);
        given(userService.findByUuid(uuid)).willReturn(userResponseDTO);

        mockMvc.perform(get("/api/usuarios/{uuid}", uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario.id").value(userResponseDTO.getId()));
    }

    @Test
    public void testShowUsuarioNoExistente() throws Exception {
        String uuid = "uuid-inexistente";
        given(userService.findByUuid(uuid)).willReturn(null);

        mockMvc.perform(get("/api/usuarios/{uuid}", uuid))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCrearUsuarioValido() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Nuevo Usuario");
        userDTO.setEmail("nuevo@example.com");
        userDTO.setPassword("ContraseñaSegura123");

        CreateResponseDTO createResponseDTO = new CreateResponseDTO();
        createResponseDTO.setId("1");
        createResponseDTO.setCreated("2020-01-01");
        createResponseDTO.setModified("2020-01-01");
        createResponseDTO.setLastLogin("2020-01-01");
        given(userService.create(any(UserDTO.class))).willReturn(createResponseDTO);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.usuario.id").value(createResponseDTO.getId()));
    }

    @Test
    public void testCrearUsuarioConErroresDeValidacion() throws Exception {
        UserDTO userDTO = new UserDTO();

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testActualizarUsuarioExistente() throws Exception {
        String uuid = "uuid-existente";
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Usuario Actualizado");
        userDTO.setEmail("actualizado@example.com");
        userDTO.setPassword("NuevaContraseña123");

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId("1");
        userResponseDTO.setName("Usuario Actualizado");
        userResponseDTO.setEmail("actualizado@example.com");
        userResponseDTO.setPhones(null);
        userResponseDTO.setCreated("2020-01-01");
        userResponseDTO.setModified("2020-01-01");
        userResponseDTO.setLastLogin("2020-01-01");
        userResponseDTO.setIsactive(true);

        given(userService.findByUuidUpdate(uuid)).willReturn(userDTO);
        given(userService.update(any(UserDTO.class))).willReturn(userResponseDTO);

        mockMvc.perform(put("/api/usuarios/{uuid}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.usuario.name").value("Usuario Actualizado"));
    }

    @Test
    public void testActualizarUsuarioNoExistente() throws Exception {
        String uuid = "uuid-inexistente";
        UserDTO userDTO = new UserDTO();

        given(userService.findByUuidUpdate(uuid)).willReturn(null);

        mockMvc.perform(put("/api/usuarios/{uuid}", uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCrearUsuarioConEmailExistente() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Usuario Existente");
        userDTO.setEmail("existente@example.com");
        userDTO.setPassword("Contraseña123");

        given(userService.existsByEmail(userDTO.getEmail())).willReturn(true);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("El correo ya se encuentra registrado en la base de datos!"));
    }

}