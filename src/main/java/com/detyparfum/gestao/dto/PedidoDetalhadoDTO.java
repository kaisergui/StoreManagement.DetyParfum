package com.detyparfum.gestao.dto;

import java.util.Date;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PedidoDetalhado", description = "Detalhamento de pedido com itens e pagamentos.")
public class PedidoDetalhadoDTO {
	 @Schema(description = "Identificador do pedido", example = "1")
	    private Long id;

	    @Schema(description = "Data do pedido", example = "2024-01-10T10:30:00Z")
	    private Date data;

	    @Schema(description = "Status do pedido", example = "PAGO")
	    private String status;

	    @Schema(description = "Observação do pedido", example = "Cliente buscou na loja.")
	    private String observacao;

	    @Schema(description = "Pagamentos vinculados")
	    private List<PagamentoDTO> pagamentos;

	    @Schema(description = "Itens do pedido")
	    private List<ItemPedidoDetalhadoDTO> itens;

	    // Getters e Setters
	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }

	    public Date getData() { return data; }
	    public void setData(Date data) { this.data = data; }

	    public String getStatus() { return status; }
	    public void setStatus(String status) { this.status = status; }

	    public String getObservacao() { return observacao; }
	    public void setObservacao(String observacao) { this.observacao = observacao; }

	    public List<PagamentoDTO> getPagamentos() { return pagamentos; }
	    public void setPagamentos(List<PagamentoDTO> pagamentos) { this.pagamentos = pagamentos; }

	    public List<ItemPedidoDetalhadoDTO> getItens() { return itens; }
	    public void setItens(List<ItemPedidoDetalhadoDTO> itens) { this.itens = itens; }
	}
