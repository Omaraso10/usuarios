package com.oalvarez.backend.usuarios.apirest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CreateResponseDTO implements Serializable {

    @JsonProperty("id")
    private String id;

    @JsonProperty("created")
    private String created;

    @JsonProperty("modified")
    private String modified;

    @JsonProperty("last_login")
    private String lastLogin;

    @JsonProperty("isactive")
    private boolean isactive;

    public CreateResponseDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    private static final long serialVersionUID = 1L;
}
