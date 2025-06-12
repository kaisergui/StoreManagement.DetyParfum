package com.detyparfum.gestao.dto;

public class ItemPedidoDetalhadoDTO {
	
    private String nomeProduto;
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