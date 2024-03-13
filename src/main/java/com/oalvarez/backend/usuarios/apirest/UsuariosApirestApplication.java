package com.oalvarez.backend.usuarios.apirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.oalvarez.backend.usuarios.apirest.*"})
public class UsuariosApirestApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuariosApirestApplication.class, args);
	}

}
