package com.oalvarez.backend.usuarios.apirest.mapper;

@FunctionalInterface
public interface IMapper <Input, Output>{

    Output map(Input request);
}