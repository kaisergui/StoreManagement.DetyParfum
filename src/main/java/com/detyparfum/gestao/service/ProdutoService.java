package com.detyparfum.gestao.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.detyparfum.gestao.dto.ProdutoDTO;
import com.detyparfum.gestao.entities.Produto;
import com.detyparfum.gestao.exception.DatabaseException;
import com.detyparfum.gestao.exception.ResourceNotFoundException;
import com.detyparfum.gestao.repository.CategoriaRepository;
import com.detyparfum.gestao.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoDTO salvar(ProdutoDTO dto) {
        try {
            Produto produto = modelMapper.map(dto, Produto.class);
            produto.setCategoria(categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com id: " + dto.getCategoriaId())));
            return modelMapper.map(produtoRepository.save(produto), ProdutoDTO.class);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao salvar produto: " + e.getMessage());
        }
    }

    public List<ProdutoDTO> listarTodos() {
        return produtoRepository.findAll().stream()
                .map(p -> modelMapper.map(p, ProdutoDTO.class))
                .collect(Collectors.toList());
    }

    public ProdutoDTO atualizar(Long id, ProdutoDTO dto) {
    	  Produto produto = produtoRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + id));

          produto.setNome(dto.getNome());
          produto.setPreco(dto.getPreco());
          produto.setMarca(dto.getMarca());
          produto.setEstoque(dto.getEstoque());

          produto = produtoRepository.save(produto);
          return modelMapper.map(produto, ProdutoDTO.class);
    }

    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado com id: " + id);
        }
        try {
            produtoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao deletar produto: dados relacionados impedem a exclusão.");
        }
    }
}
