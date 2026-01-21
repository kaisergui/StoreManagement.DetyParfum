package com.detyparfum.gestao.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ItemPedidoDetalhado", description = "Detalhes simplificados do item do pedido.")
public class ItemPedidoDetalhadoDTO {
	
    @Schema(description = "Nome do produto", example = "Perfume Floral")
    private String nomeProduto;

    @Schema(description = "Preço do item", example = "199.90")
    private Double preco;

    public ItemPedidoDetalhadoDTO(String nomeProduto, Double preco) {
        this.nomeProduto = nomeProduto;
        this.preco = preco;
    }

    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }
}
