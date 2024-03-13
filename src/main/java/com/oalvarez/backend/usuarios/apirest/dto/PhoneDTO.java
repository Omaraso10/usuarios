package com.oalvarez.backend.usuarios.apirest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PhoneDTO implements Serializable {

    @JsonProperty("number")
    private String number;
    @JsonProperty("citycode")
    private String cityCode;
    @JsonProperty("countrycode")
    private String countryCode;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    private static final long serialVersionUID = 1L;
}