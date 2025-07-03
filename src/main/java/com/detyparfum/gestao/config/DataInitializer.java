package com.detyparfum.gestao.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.detyparfum.gestao.entities.Admin;
import com.detyparfum.gestao.entities.Categoria;
import com.detyparfum.gestao.repository.AdminRepository;
import com.detyparfum.gestao.repository.CategoriaRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(CategoriaRepository categoriaRepository,
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder) {
this.categoriaRepository = categoriaRepository;
this.adminRepository = adminRepository;
this.passwordEncoder = passwordEncoder;
}

    @Override
    public void run(String... args) {
        List<String> categoriasFixas = List.of(
                "Maquiagens",
                "Cuidados com a Pele",
                "Perfume",
                "Skin Care",
                "Avon - Moda & casa",
                "Haircare",
                "Diversos"
        );

        for (String nome : categoriasFixas) {
            boolean existe = categoriaRepository.existsByNomeIgnoreCase(nome);
            if (!existe) {
                categoriaRepository.save(new Categoria(nome));
            }
        }
    
    
    
    String adminEmail = "claudetelucecanabarro@gmail.com";
    
    if (adminRepository.findByEmail(adminEmail).isEmpty()) {
        Admin admin = new Admin();
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode("admin123"));
        adminRepository.save(admin);
    }
    }
}