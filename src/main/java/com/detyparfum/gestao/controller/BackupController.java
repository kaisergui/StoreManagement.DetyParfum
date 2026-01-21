package com.detyparfum.gestao.controller;


import com.detyparfum.gestao.service.BackupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/backup")
@Tag(name = "Backup", description = "Operações de backup e restauração de dados.")
public class BackupController {

    private final BackupService backupService;

    public BackupController(BackupService backupService) {
        this.backupService = backupService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar backups", description = "Lista os arquivos de backup disponíveis.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de backups",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class))))
    })
    public List<String> listarBackups() {
        File pasta = new File("backups");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
        String[] arquivos = pasta.list((dir, name) -> name.endsWith(".sql"));
        return arquivos != null ? Arrays.asList(arquivos) : List.of();
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar backup", description = "Gera um novo arquivo de backup.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Backup gerado com sucesso",
            content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<String> criarBackup() {
        String nome = backupService.realizarBackup();
        return ResponseEntity.ok(nome);
    }

    @GetMapping("/download/{nome}")
    @Operation(summary = "Baixar backup", description = "Faz o download de um arquivo de backup.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Arquivo de backup",
            content = @Content(mediaType = "application/octet-stream",
                schema = @Schema(type = "string", format = "binary"))),
        @ApiResponse(responseCode = "404", description = "Arquivo não encontrado", content = @Content)
    })
    public ResponseEntity<InputStreamResource> downloadBackup(
        @Parameter(description = "Nome do arquivo de backup", example = "backup-2024-01-10.sql")
        @PathVariable String nome
    ) throws IOException {
        File arquivo = new File("backups/" + nome);
        if (!arquivo.exists()) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource recurso = new InputStreamResource(new FileInputStream(arquivo));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nome + "\"")
                .contentLength(arquivo.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(recurso);
    }

    @DeleteMapping("/{nome}")
    @Operation(summary = "Excluir backup", description = "Remove um arquivo de backup.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Backup excluído"),
        @ApiResponse(responseCode = "404", description = "Arquivo não encontrado", content = @Content)
    })
    public ResponseEntity<Void> excluirBackup(
        @Parameter(description = "Nome do arquivo de backup", example = "backup-2024-01-10.sql")
        @PathVariable String nome
    ) {
        File arquivo = new File("backups/" + nome);
        if (arquivo.exists() && arquivo.delete()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
