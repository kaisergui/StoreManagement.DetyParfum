package com.detyparfum.gestao.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Cliente", description = "Dados principais do cliente.")
public class ClienteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Identificador do cliente", example = "1")
    private Long id;

    @Schema(description = "Nome completo", example = "Maria Silva")
    private String nome;

    @Schema(description = "CPF do cliente", example = "123.456.789-00")
    private String cpf;

    @Schema(description = "Telefone de contato", example = "(11) 99999-9999")
    private String telefone;

    @Schema(description = "E-mail do cliente", example = "maria@exemplo.com")
    private String email;

    public ClienteDTO() {}

    public ClienteDTO(Long id, String nome, String cpf, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    
}
