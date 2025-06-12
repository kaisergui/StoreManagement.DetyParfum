package com.detyparfum.gestao.dto;

import java.io.Serializable;
import java.util.List;

public class ClienteDetalhesDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private List<PedidoDetalhadoDTO> pedidos;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<PedidoDetalhadoDTO> getPedidos() { return pedidos; }
    public void setPedidos(List<PedidoDetalhadoDTO> pedidos) { this.pedidos = pedidos; }
    
    
}
