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

import com.detyparfum.gestao.dto.PagamentoDTO;
import com.detyparfum.gestao.service.PagamentoService;

@RestController
@RequestMapping("/pagamentos")
@Tag(name = "Pagamentos", description = "Operações de registro e atualização de pagamentos.")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    @Operation(summary = "Registrar pagamento", description = "Cria um novo registro de pagamento.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pagamento registrado",
            content = @Content(schema = @Schema(implementation = PagamentoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public ResponseEntity<PagamentoDTO> salvar(@RequestBody PagamentoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoService.salvar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar pagamentos", description = "Retorna todos os pagamentos cadastrados.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de pagamentos",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PagamentoDTO.class))))
    })
    public ResponseEntity<List<PagamentoDTO>> listarTodos() {
        return ResponseEntity.ok(pagamentoService.listarTodos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pagamento", description = "Atualiza um pagamento existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pagamento atualizado",
            content = @Content(schema = @Schema(implementation = PagamentoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Pagamento não encontrado", content = @Content)
    })
    public ResponseEntity<PagamentoDTO> atualizar(
        @Parameter(description = "ID do pagamento", example = "1")
        @PathVariable Long id,
        @RequestBody PagamentoDTO dto
    ) {
        return ResponseEntity.ok(pagamentoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pagamento", description = "Remove um pagamento pelo ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pagamento excluído"),
        @ApiResponse(responseCode = "404", description = "Pagamento não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deletar(
        @Parameter(description = "ID do pagamento", example = "1")
        @PathVariable Long id
    ) {
        pagamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
