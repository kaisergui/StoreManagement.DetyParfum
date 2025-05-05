package com.detyparfum.gestao.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusPedido {
	  	CANCELADO("Cancelado"),
	    AGUARDANDO_PAGAMENTO("Aguardando Pagamento"),
	    PAGO("Pago");

	    private final String descricao;

	    StatusPedido(String descricao) {
	        this.descricao = descricao;
	    }

	    @JsonValue
	    public String getDescricao() {
	        return descricao;
	    }

	    @JsonCreator
	    public static StatusPedido fromValue(String value) {
	        for (StatusPedido status : StatusPedido.values()) {
	            if (status.getDescricao().equalsIgnoreCase(value)) {
	                return status;
	            }
	        }
	        throw new IllegalArgumentException("Status inválido: " + value);
	    }

	    @Override
	    public String toString() {
	        return descricao;
	    }
}
