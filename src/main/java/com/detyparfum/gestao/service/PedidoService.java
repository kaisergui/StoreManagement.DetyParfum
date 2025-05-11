package com.detyparfum.gestao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.detyparfum.gestao.dto.ItemPedidoDTO;
import com.detyparfum.gestao.dto.PedidoDTO;
import com.detyparfum.gestao.entities.ItemPedido;
import com.detyparfum.gestao.entities.Pedido;
import com.detyparfum.gestao.entities.Produto;
import com.detyparfum.gestao.entities.enums.StatusPedido;
import com.detyparfum.gestao.exception.DatabaseException;
import com.detyparfum.gestao.exception.ResourceNotFoundException;
import com.detyparfum.gestao.repository.ClienteRepository;
import com.detyparfum.gestao.repository.PedidoRepository;
import com.detyparfum.gestao.repository.ProdutoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PedidoDTO salvar(PedidoDTO dto) {
        try {
            Pedido pedido = new Pedido();
            modelMapper.map(dto, pedido);

            pedido.setCliente(clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + dto.getClienteId())));

            pedido.setStatus(dto.getStatus() != null ? dto.getStatus() : StatusPedido.AGUARDANDO_PAGAMENTO);
            pedido.setItens(new ArrayList<>());

            if (dto.getItens() != null) {
                for (ItemPedidoDTO itemDTO : dto.getItens()) {
                    Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + itemDTO.getProdutoId()));

                    ItemPedido item = new ItemPedido();
                    item.setProduto(produto);
                    item.setQuantidade(itemDTO.getQuantidade());
                    item.setPreco(produto.getPreco());
                    item.setPedido(pedido);

                    pedido.getItens().add(item);
                }
            }

            pedido = pedidoRepository.save(pedido);
            return modelMapper.map(pedido, PedidoDTO.class);

        } catch (Exception e) {
            throw new DatabaseException("Erro ao salvar pedido: " + e.getMessage());
        }
    }

    public List<PedidoDTO> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(p -> modelMapper.map(p, PedidoDTO.class))
                .collect(Collectors.toList());
    }

    public PedidoDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + id));
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    public PedidoDTO atualizar(Long id, PedidoDTO dto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + id));

        pedido.setData(dto.getData());
        pedido.setStatus(dto.getStatus());
        pedido.setObservacao(dto.getObservacao());
        pedido.setCliente(clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + dto.getClienteId())));

        pedido.getItens().clear();
        if (dto.getItens() != null) {
            for (ItemPedidoDTO itemDTO : dto.getItens()) {
                Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                        .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + itemDTO.getProdutoId()));

                ItemPedido item = new ItemPedido();
                item.setProduto(produto);
                item.setQuantidade(itemDTO.getQuantidade());
                item.setPreco(produto.getPreco());
                item.setPedido(pedido);

                pedido.getItens().add(item);
            }
        }

        return modelMapper.map(pedidoRepository.save(pedido), PedidoDTO.class);
    }

    public void deletar(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido não encontrado com id: " + id);
        }
        try {
            pedidoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao deletar pedido: dados relacionados impedem a exclusão.");
        }
    }
}