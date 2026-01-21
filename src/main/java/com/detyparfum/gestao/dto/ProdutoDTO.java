package com.detyparfum.gestao.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Produto", description = "Representa um produto disponível no catálogo.")
public class ProdutoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Identificador do produto", example = "1")
    private Long id;

    @Schema(description = "Nome do produto", example = "Perfume Floral")
    private String nome;

    @Schema(description = "Preço do produto", example = "199.90")
    private Double preco;

    @Schema(description = "Quantidade em estoque", example = "25")
    private Integer estoque;

    @Schema(description = "Marca do produto", example = "Dety Parfum")
    private String marca;

    @Schema(description = "Identificador da categoria associada", example = "2")
    private Long categoriaId;

    public ProdutoDTO() {}

    public ProdutoDTO(Long id, String nome, Double preco, Integer estoque, String marca, Long categoriaId) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.marca = marca;
        this.categoriaId = categoriaId;
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

}
