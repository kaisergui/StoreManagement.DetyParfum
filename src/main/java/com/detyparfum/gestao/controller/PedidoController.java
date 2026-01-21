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

import com.detyparfum.gestao.dto.PedidoDTO;
import com.detyparfum.gestao.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Operações de criação e manutenção de pedidos.")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @Operation(summary = "Cadastrar pedido", description = "Cria um novo pedido.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso",
            content = @Content(schema = @Schema(implementation = PedidoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public ResponseEntity<PedidoDTO> salvar(@RequestBody PedidoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.salvar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar pedidos", description = "Retorna todos os pedidos cadastrados.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de pedidos",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PedidoDTO.class))))
    })
    public ResponseEntity<List<PedidoDTO>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna os dados de um pedido específico.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido encontrado",
            content = @Content(schema = @Schema(implementation = PedidoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content)
    })
    public ResponseEntity<PedidoDTO> buscarPorId(
        @Parameter(description = "ID do pedido", example = "1")
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pedido", description = "Atualiza as informações de um pedido.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido atualizado",
            content = @Content(schema = @Schema(implementation = PedidoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content)
    })
    public ResponseEntity<PedidoDTO> atualizar(
        @Parameter(description = "ID do pedido", example = "1")
        @PathVariable Long id,
        @RequestBody PedidoDTO dto
    ) {
        return ResponseEntity.ok(pedidoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pedido", description = "Remove um pedido pelo ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pedido excluído"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deletar(
        @Parameter(description = "ID do pedido", example = "1")
        @PathVariable Long id
    ) {
        pedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{pedidoId}/itens/{itemId}")
    @Operation(summary = "Remover item do pedido", description = "Remove um item específico de um pedido.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Item removido"),
        @ApiResponse(responseCode = "404", description = "Pedido ou item não encontrado", content = @Content)
    })
    public ResponseEntity<Void> removerItem(
        @Parameter(description = "ID do pedido", example = "1")
        @PathVariable Long pedidoId,
        @Parameter(description = "ID do item do pedido", example = "1")
        @PathVariable Long itemId
    ) {
        pedidoService.removerItem(pedidoId, itemId);
        return ResponseEntity.noContent().build();
    }
    
    
}
