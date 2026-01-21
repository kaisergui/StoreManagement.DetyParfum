package com.detyparfum.gestao.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ItemPedido", description = "Item de um pedido com quantidade e preço.")
public class ItemPedidoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Identificador do item", example = "1")
    private Long id;

    @Schema(description = "Quantidade do item", example = "2")
    private Integer quantidade;

    @Schema(description = "Preço unitário do item", example = "199.90")
    private Double preco;

    @Schema(description = "Identificador do produto", example = "1")
    private Long produtoId;

    @Schema(description = "Identificador do pedido", example = "1")
    private Long pedidoId;

    public ItemPedidoDTO() {}

    public ItemPedidoDTO(Long id, Integer quantidade, Double preco, Long produtoId, Long pedidoId) {
        this.id = id;
        this.quantidade = quantidade;
        this.preco = preco;
        this.produtoId = produtoId;
        this.pedidoId = pedidoId;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}


}
