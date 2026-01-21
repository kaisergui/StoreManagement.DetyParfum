package com.detyparfum.gestao.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.detyparfum.gestao.dto.ClienteDTO;
import com.detyparfum.gestao.dto.ClienteDetalhesDTO;
import com.detyparfum.gestao.service.ClienteService;



@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Operações de cadastro e consulta de clientes.")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    @Operation(summary = "Cadastrar cliente", description = "Cria um novo cliente.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso",
            content = @Content(schema = @Schema(implementation = ClienteDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public ResponseEntity<ClienteDTO> salvar(@RequestBody ClienteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.salvar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar clientes", description = "Retorna todos os clientes cadastrados.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de clientes",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClienteDTO.class))))
    })
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna os dados de um cliente específico.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado",
            content = @Content(schema = @Schema(implementation = ClienteDTO.class))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    public ResponseEntity<ClienteDTO> buscarPorId(
        @Parameter(description = "ID do cliente", example = "1")
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza as informações de um cliente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente atualizado",
            content = @Content(schema = @Schema(implementation = ClienteDTO.class))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    public ResponseEntity<ClienteDTO> atualizar(
        @Parameter(description = "ID do cliente", example = "1")
        @PathVariable Long id,
        @RequestBody ClienteDTO dto
    ) {
        return ResponseEntity.ok(clienteService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir cliente", description = "Remove um cliente pelo ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cliente excluído"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deletar(
        @Parameter(description = "ID do cliente", example = "1")
        @PathVariable Long id
    ) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}/detalhes")
    @Operation(summary = "Detalhar cliente", description = "Retorna informações detalhadas do cliente com pedidos e pagamentos.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Detalhes do cliente",
            content = @Content(schema = @Schema(implementation = ClienteDetalhesDTO.class))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    public ResponseEntity<ClienteDetalhesDTO> getDetalhesCliente(
        @Parameter(description = "ID do cliente", example = "1")
        @PathVariable Long id
    ) {
        ClienteDetalhesDTO dto = clienteService.obterDetalhesCliente(id);
        return ResponseEntity.ok(dto);
    }

}
