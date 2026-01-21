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

import com.detyparfum.gestao.dto.CategoriaDTO;
import com.detyparfum.gestao.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias", description = "Operações de cadastro e manutenção de categorias.")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    @Operation(summary = "Cadastrar categoria", description = "Cria uma nova categoria de produtos.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso",
            content = @Content(schema = @Schema(implementation = CategoriaDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public ResponseEntity<CategoriaDTO> salvar(@RequestBody CategoriaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.salvar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar categorias", description = "Retorna todas as categorias cadastradas.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de categorias",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoriaDTO.class))))
    })
    public ResponseEntity<List<CategoriaDTO>> listarTodos() {
        return ResponseEntity.ok(categoriaService.listarTodos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar categoria", description = "Atualiza uma categoria existente pelo ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoria atualizada",
            content = @Content(schema = @Schema(implementation = CategoriaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content)
    })
    public ResponseEntity<CategoriaDTO> atualizar(
        @Parameter(description = "ID da categoria", example = "1")
        @PathVariable Long id,
        @RequestBody CategoriaDTO dto
    ) {
        return ResponseEntity.ok(categoriaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir categoria", description = "Remove uma categoria pelo ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Categoria excluída"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content)
    })
    public ResponseEntity<Void> deletar(
        @Parameter(description = "ID da categoria", example = "1")
        @PathVariable Long id
    ) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
