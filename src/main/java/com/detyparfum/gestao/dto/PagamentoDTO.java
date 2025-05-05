package com.detyparfum.gestao.dto;

import java.io.Serializable;

public class PagamentoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String tipo;
    private Integer parcelas;
    private Double valor;
    private Long pedidoId;

    public PagamentoDTO() {}

    public PagamentoDTO(Long id, String tipo, Integer parcelas, Double valor, Long pedidoId) {
        this.id = id;
        this.tipo = tipo;
        this.parcelas = parcelas;
        this.valor = valor;
        this.pedidoId = pedidoId;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getParcelas() {
		return parcelas;
	}

	public void setParcelas(Integer parcelas) {
		this.parcelas = parcelas;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}
    

}
