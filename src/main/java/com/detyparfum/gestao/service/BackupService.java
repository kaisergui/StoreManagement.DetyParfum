package com.detyparfum.gestao.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BackupService {

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPass;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    private final String backupDir = "backups";

    public String realizarBackup() {
        try {
            // Garante que a pasta exista
            File pasta = new File(backupDir);
            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            String nomeArquivo = "backup_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".sql";
            String caminhoPgDump = "\"C:\\Program Files\\PostgreSQL\\17\\bin\\pg_dump.exe\"";
            String nomeBanco = extrairNomeBanco(dbUrl);

            String comando = String.format("%s -U %s -F c -f %s/%s %s", caminhoPgDump, dbUser, backupDir, nomeArquivo, nomeBanco);

            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", comando);
            builder.environment().put("PGPASSWORD", dbPass);
            builder.directory(new File(".")); // executa na raiz do projeto

            Process processo = builder.start();

            if (processo.waitFor() == 0) {
                return nomeArquivo;
            } else {
                throw new RuntimeException("Erro ao executar comando de backup.");
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao realizar backup: " + e.getMessage(), e);
        }
    }

    public List<String> listarBackups() {
        File pasta = new File(backupDir);
        if (!pasta.exists()) return List.of();

        String[] arquivos = pasta.list((dir, name) -> name.endsWith(".sql"));
        return arquivos != null ? Arrays.asList(arquivos) : List.of();
    }

    public void excluirBackup(String nome) {
        File arquivo = new File(backupDir + "/" + nome);
        if (!arquivo.exists() || !arquivo.delete()) {
            throw new RuntimeException("Não foi possível excluir o backup: " + nome);
        }
    }

    public InputStream downloadBackup(String nome) throws FileNotFoundException {
        File arquivo = new File(backupDir + "/" + nome);
        if (!arquivo.exists()) {
            throw new FileNotFoundException("Arquivo não encontrado: " + nome);
        }
        return new FileInputStream(arquivo);
    }

    private String extrairNomeBanco(String url) {
        // Exemplo: jdbc:postgresql://localhost:5432/gestao
        return url.substring(url.lastIndexOf("/") + 1);
    }
}