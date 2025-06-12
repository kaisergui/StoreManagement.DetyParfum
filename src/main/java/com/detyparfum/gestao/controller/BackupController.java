package com.detyparfum.gestao.controller;


import com.detyparfum.gestao.service.BackupService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/backup")
public class BackupController {

    private final BackupService backupService;

    public BackupController(BackupService backupService) {
        this.backupService = backupService;
    }

    @GetMapping("/listar")
    public List<String> listarBackups() {
        File pasta = new File("backups");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
        String[] arquivos = pasta.list((dir, name) -> name.endsWith(".sql"));
        return arquivos != null ? Arrays.asList(arquivos) : List.of();
    }

    @PostMapping("/criar")
    public ResponseEntity<String> criarBackup() {
        String nome = backupService.realizarBackup();
        return ResponseEntity.ok(nome);
    }

    @GetMapping("/download/{nome}")
    public ResponseEntity<InputStreamResource> downloadBackup(@PathVariable String nome) throws IOException {
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
    public ResponseEntity<Void> excluirBackup(@PathVariable String nome) {
        File arquivo = new File("backups/" + nome);
        if (arquivo.exists() && arquivo.delete()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}