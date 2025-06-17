package com.detyparfum.gestao.config;

import com.detyparfum.gestao.entities.Categoria;
import com.detyparfum.gestao.repository.CategoriaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;

    public DataInitializer(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public void run(String... args) {
        List<String> categoriasFixas = List.of(
                "Maquiagens",
                "Cuidados com a Pele",
                "Perfume",
                "Skin Care"
        );

        for (String nome : categoriasFixas) {
            boolean existe = categoriaRepository.existsByNomeIgnoreCase(nome);
            if (!existe) {
                categoriaRepository.save(new Categoria(nome));
            }
        }
    }
}