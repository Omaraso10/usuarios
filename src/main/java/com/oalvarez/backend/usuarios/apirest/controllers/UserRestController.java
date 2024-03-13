package com.oalvarez.backend.usuarios.apirest.controllers;

import com.oalvarez.backend.usuarios.apirest.dto.CreateResponseDTO;
import com.oalvarez.backend.usuarios.apirest.dto.UserDTO;
import com.oalvarez.backend.usuarios.apirest.dto.UserResponseDTO;
import com.oalvarez.backend.usuarios.apirest.services.IUserService;
import com.oalvarez.backend.usuarios.apirest.util.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final Logger log = LoggerFactory.getLogger(UserRestController.class);

    private final IUserService userService;

    public UserRestController(IUserService userService){
        this.userService = userService;
    }

    @GetMapping("/usuarios")
    public ResponseEntity<?> obtieneUsuarios(){
        List<UserResponseDTO> usersDTO = null;
        Map<String, Object> response = new HashMap<>();
        try{
            usersDTO = userService.findAll();
        }catch(DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("usuarios", usersDTO);
        response.put("mensaje" , "OK");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @GetMapping("/usuarios/{uuid}")
    public ResponseEntity<?> show(@PathVariable String uuid){
        UserResponseDTO userDTO = null;
        Map<String, Object> response = new HashMap<>();
        try{
            userDTO = userService.findByUuid(uuid);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (userDTO == null){
            response.put("mensaje", "El usuario UUID: ".concat(uuid.concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("usuario", userDTO);
        response.put("mensaje" , "OK");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<?> create(@Valid @RequestBody UserDTO userDTO, BindingResult result){
        CreateResponseDTO userNew = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "el campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("mensaje", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if(!Helper.isValidPassword(userDTO.getPassword())){
            response.put("mensaje", "La password no cumple con las políticas de seguridad.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if(!Helper.isValidEmail(userDTO.getEmail())){
            response.put("mensaje", "El mail ingresado no es una dirección de correo valida");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            if(userService.existsByEmail(userDTO.getEmail())){
                response.put("mensaje", "Error al crear el usuario en la base de datos.");
                response.put("error", "El correo ya se encuentra registrado en la base de datos!");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            userNew = userService.create(userDTO);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al crear el usuario en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El usuario fue creado con éxito!");
        response.put("usuario", userNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/usuarios/{uuid}")
    public ResponseEntity<?> update(@Valid @RequestBody UserDTO userDTO, BindingResult result, @PathVariable String uuid) {
        UserDTO userCurrent = userService.findByUuidUpdate(uuid);
        UserResponseDTO userUpdated = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if(!Helper.isValidPassword(userDTO.getPassword())){
            response.put("mensaje", "La password no cumple con las políticas de seguridad.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if(!Helper.isValidEmail(userDTO.getEmail())){
            response.put("mensaje", "El mail ingresado no es una dirección de correo valida");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (userCurrent == null) {
            response.put("mensaje", "Error: no se pudo editar el usuario con UUID: ".concat(uuid.concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            userCurrent.setName(userDTO.getName());
            userCurrent.setIsactive(userDTO.isIsactive());
            userCurrent.setPhones(userDTO.getPhones());
            userCurrent.setPassword(userDTO.getPassword());
            userUpdated = userService.update(userCurrent);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el usuario en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El usuario fue actualizado con éxito!");
        response.put("usuario", userUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

}