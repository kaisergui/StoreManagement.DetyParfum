package com.detyparfum.gestao.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Pagamento", description = "Informações de pagamento de um pedido.")
public class PagamentoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Identificador do pagamento", example = "1")
    private Long id;

    @Schema(description = "Tipo de pagamento", example = "CARTAO")
    private String tipo;

    @Schema(description = "Quantidade de parcelas", example = "3")
    private Integer parcelas;

    @Schema(description = "Valor do pagamento", example = "299.90")
    private Double valor;

    @Schema(description = "Identificador do pedido", example = "1")
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
