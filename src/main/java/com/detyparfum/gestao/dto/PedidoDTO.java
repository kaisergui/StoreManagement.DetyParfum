package com.detyparfum.gestao.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.detyparfum.gestao.entities.enums.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Pedido", description = "Representa um pedido com itens e pagamentos.")
public class PedidoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Identificador do pedido", example = "1")
    private Long id;

    @Schema(description = "Data de criação do pedido", example = "2024-01-10T10:30:00Z")
    private Date data;

    @Schema(description = "Status do pedido", example = "AGUARDANDO_PAGAMENTO")
    private StatusPedido status;

    @Schema(description = "Observações adicionais", example = "Entregar no período da tarde.")
    private String observacao;

    @Schema(description = "Identificador do cliente", example = "1")
    private Long clienteId;

    @Schema(description = "Itens do pedido")
    private List<ItemPedidoDTO> itens;

    @Schema(description = "Pagamentos vinculados ao pedido")
    private List<PagamentoDTO> pagamentos;

    public PedidoDTO() {}

    public PedidoDTO(Long id, Date data, StatusPedido status, String observacao, Long clienteId, List<ItemPedidoDTO> itens, List<PagamentoDTO> pagamentos) {
        this.id = id;
        this.data = data;
        this.status = status;
        this.observacao = observacao;
        this.clienteId = clienteId;
        this.itens = itens;
        this.pagamentos = pagamentos;
    }

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

    public List<PagamentoDTO> getPagamentos() { return pagamentos; }
    public void setPagamentos(List<PagamentoDTO> pagamentos) { this.pagamentos = pagamentos; }
}
