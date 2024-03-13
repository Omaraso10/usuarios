package com.oalvarez.backend.usuarios.apirest.dao;

import com.oalvarez.backend.usuarios.apirest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserDao extends JpaRepository<User, Long> {

    public User findByEmail(String email);
    public User findByUuid(String uuid);
    public boolean existsByEmail(String email);

}