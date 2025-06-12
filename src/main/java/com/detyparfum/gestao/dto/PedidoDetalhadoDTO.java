package com.detyparfum.gestao.dto;

import java.util.Date;
import java.util.List;

public class PedidoDetalhadoDTO {
	 private Long id;
	    private Date data;
	    private String status;
	    private String observacao;
	    private List<PagamentoDTO> pagamentos;
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
