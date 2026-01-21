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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.detyparfum.gestao.dto.ProdutoDTO;
import com.detyparfum.gestao.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Operações de cadastro e consulta de produtos.")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    @Operation(summary = "Cadastrar produto", description = "Cria um novo produto no catálogo.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
            content = @Content(schema = @Schema(implementation = ProdutoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public ResponseEntity<ProdutoDTO> salvar(@RequestBody ProdutoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar produtos", description = "Retorna todos os produtos cadastrados.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de produtos",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProdutoDTO.class))))
    })
    public ResponseEntity<List<ProdutoDTO>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar produtos por nome", description = "Pesquisa produtos contendo o nome informado.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de produtos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProdutoDTO.class)))),
        @ApiResponse(responseCode = "400", description = "Parâmetro inválido", content = @Content)
    })
    public ResponseEntity<List<ProdutoDTO>> buscarPorNome(
        @Parameter(description = "Nome ou parte do nome do produto", required = true)
        @RequestParam String nome
    ) {
        return ResponseEntity.ok(produtoService.buscarPorNome(nome));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza um produto existente pelo ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto atualizado",
            content = @Content(schema = @Schema(implementation = ProdutoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
    })
    public ResponseEntity<ProdutoDTO> atualizar(
        @Parameter(description = "ID do produto", example = "1")
        @PathVariable Long id,
        @RequestBody ProdutoDTO dto
    ) {
        return ResponseEntity.ok(produtoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir produto", description = "Remove um produto pelo ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Produto excluído"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deletar(
        @Parameter(description = "ID do produto", example = "1")
        @PathVariable Long id
    ) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
