package com.detyparfum.gestao.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.detyparfum.gestao.dto.CategoriaDTO;
import com.detyparfum.gestao.entities.Categoria;
import com.detyparfum.gestao.exception.DatabaseException;
import com.detyparfum.gestao.exception.ResourceNotFoundException;
import com.detyparfum.gestao.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CategoriaDTO salvar(CategoriaDTO dto) {
        try {
            Categoria categoria = modelMapper.map(dto, Categoria.class);
            return modelMapper.map(categoriaRepository.save(categoria), CategoriaDTO.class);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao salvar categoria: " + e.getMessage());
        }
    }

    public List<CategoriaDTO> listarTodos() {
        return categoriaRepository.findAll().stream()
                .map(c -> modelMapper.map(c, CategoriaDTO.class))
                .collect(Collectors.toList());
    }

    public CategoriaDTO atualizar(Long id, CategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com id: " + id));
        categoria.setNome(dto.getNome());
        categoria = categoriaRepository.save(categoria);
        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    public void deletar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada com id: " + id);
        }
        try {
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao deletar categoria: dados relacionados impedem a exclusão.");
        }
    }
}