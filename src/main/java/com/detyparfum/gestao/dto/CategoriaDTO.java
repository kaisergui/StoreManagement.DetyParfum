package com.detyparfum.gestao.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Categoria", description = "Categoria de produtos.")
public class CategoriaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Identificador da categoria", example = "1")
    private Long id;

    @Schema(description = "Nome da categoria", example = "Perfumaria")
    private String nome;

    public CategoriaDTO() {}

    public CategoriaDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
