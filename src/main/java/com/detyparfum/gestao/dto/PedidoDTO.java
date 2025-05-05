package com.detyparfum.gestao.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.detyparfum.gestao.entities.enums.StatusPedido;

public class PedidoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Date data;
    private StatusPedido status;
    private String observacao;
    private Long clienteId;
    private List<ItemPedidoDTO> itens;

    public PedidoDTO() {}

    public PedidoDTO(Long id, Date data, StatusPedido status, String observacao, Long clienteId, List<ItemPedidoDTO> itens) {
        this.id = id;
        this.data = data;
        this.status = status;
        this.observacao = observacao;
        this.clienteId = clienteId;
        this.itens = itens;
    }

    // Getters e Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public List<ItemPedidoDTO> getItens() { return itens; }
    public void setItens(List<ItemPedidoDTO> itens) { this.itens = itens; }
    
}
