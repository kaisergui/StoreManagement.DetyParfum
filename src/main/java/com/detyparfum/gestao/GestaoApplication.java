package com.detyparfum.gestao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GestaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoApplication.class, args);
	}

}
