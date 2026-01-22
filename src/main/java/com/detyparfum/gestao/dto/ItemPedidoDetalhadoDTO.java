package com.detyparfum.gestao.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ItemPedidoDetalhado", description = "Detalhes simplificados do item do pedido.")
public class ItemPedidoDetalhadoDTO {
	
    @Schema(description = "Nome do produto", example = "Perfume Floral")
    private String nomeProduto;

    @Schema(description = "Preço do item", example = "199.90")
    private Double preco;

    @Schema(description = "Quantidade do item", example = "2")
    private Integer quantidade;

    public ItemPedidoDetalhadoDTO(String nomeProduto, Double preco, Integer quantidade) {
        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}
