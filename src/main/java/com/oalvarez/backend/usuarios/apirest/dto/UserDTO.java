package com.oalvarez.backend.usuarios.apirest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

public class UserDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @NotEmpty(message = "no puede estar vacio")
    @Size(min = 4, max = 50, message = "el tamaño tiene que estar entre 4 y 50")
    @JsonProperty("name")
    private String name;

    @NotEmpty(message = "no puede estar vacio")
    @JsonProperty("password")
    private String password;

    @NotEmpty(message = "no puede estar vacio")
    @JsonProperty("email")
    private String email;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("phones")
    private List<PhoneDTO> phones;

    @JsonProperty("created")
    private String created;

    @JsonProperty("modified")
    private String modified;

    @JsonProperty("last_login")
    private String lastLogin;

    @JsonProperty("isactive")
    private boolean isactive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private static final long serialVersionUID = 1L;
}