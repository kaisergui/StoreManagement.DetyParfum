package com.detyparfum.gestao.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.detyparfum.gestao.dto.PagamentoDTO;
import com.detyparfum.gestao.entities.Pagamento;
import com.detyparfum.gestao.entities.Pedido;
import com.detyparfum.gestao.exception.DatabaseException;
import com.detyparfum.gestao.exception.ResourceNotFoundException;
import com.detyparfum.gestao.repository.PagamentoRepository;
import com.detyparfum.gestao.repository.PedidoRepository;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PagamentoDTO salvar(PagamentoDTO dto) {
        try {
            Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
            pagamento.setPedido(pedidoRepository.findById(dto.getPedidoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + dto.getPedidoId())));
            return modelMapper.map(pagamentoRepository.save(pagamento), PagamentoDTO.class);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao salvar pagamento: " + e.getMessage());
        }
    }

    public List<PagamentoDTO> listarTodos() {
        return pagamentoRepository.findAll().stream()
                .map(p -> modelMapper.map(p, PagamentoDTO.class))
                .collect(Collectors.toList());
    }

    public PagamentoDTO atualizar(Long id, PagamentoDTO dto) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento não encontrado com id: " + id));
        pagamento.setTipo(dto.getTipo());
        pagamento.setParcelas(dto.getParcelas());
        pagamento.setValor(dto.getValor());

        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + dto.getPedidoId()));
        pagamento.setPedido(pedido);

        pagamento = pagamentoRepository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public void deletar(Long id) {
        if (!pagamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pagamento não encontrado com id: " + id);
        }
        try {
            pagamentoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao deletar pagamento: dados relacionados impedem a exclusão.");
        }
    }
}