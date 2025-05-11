package com.detyparfum.gestao.controller;

import java.util.List;

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
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<PagamentoDTO> salvar(@RequestBody PagamentoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoService.salvar(dto));
    }

    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> listarTodos() {
        return ResponseEntity.ok(pagamentoService.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> atualizar(@PathVariable Long id, @RequestBody PagamentoDTO dto) {
        return ResponseEntity.ok(pagamentoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pagamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}