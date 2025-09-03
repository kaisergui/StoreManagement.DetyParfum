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
import com.detyparfum.gestao.dto.PagamentoDTO;
import com.detyparfum.gestao.entities.ItemPedido;
import com.detyparfum.gestao.entities.Pagamento;
import com.detyparfum.gestao.entities.Pedido;
import com.detyparfum.gestao.entities.Produto;
import com.detyparfum.gestao.entities.enums.StatusPedido;
import com.detyparfum.gestao.exception.DatabaseException;
import com.detyparfum.gestao.exception.ResourceNotFoundException;
import com.detyparfum.gestao.repository.ClienteRepository;
import com.detyparfum.gestao.repository.ItemPedidoRepository;
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
    
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public PedidoDTO salvar(PedidoDTO dto) {
        try {
            Pedido pedido = new Pedido();
            modelMapper.map(dto, pedido);

            pedido.setCliente(clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + dto.getClienteId())));

            pedido.setStatus(dto.getStatus() != null ? dto.getStatus() : pedido.getStatus());
            pedido.setItens(new ArrayList<>());
            pedido.setPagamentos(new ArrayList<>()); // ← necessário para iniciar a lista de pagamentos

         // Itens
            if (dto.getItens() != null) {
                for (ItemPedidoDTO itemDTO : dto.getItens()) {
                    Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + itemDTO.getProdutoId()));

                    boolean precisaSalvar = false;

                    // Atualiza estoque somente se > 0
                    if (produto.getEstoque() != null && produto.getEstoque() > 0) {
                        int novoEstoque = produto.getEstoque() - itemDTO.getQuantidade();
                        produto.setEstoque(Math.max(novoEstoque, 0));
                        precisaSalvar = true;
                    }

                    // Atualiza preço do produto se for diferente
                    if (itemDTO.getPreco() != null && !produto.getPreco().equals(itemDTO.getPreco())) {
                        produto.setPreco(itemDTO.getPreco());
                        precisaSalvar = true;
                    }

                    // Salva o produto apenas se houve alteração
                    if (precisaSalvar) {
                        produtoRepository.save(produto);
                    }

                    // Cria item do pedido
                    ItemPedido item = new ItemPedido();
                    item.setProduto(produto);
                    item.setQuantidade(itemDTO.getQuantidade());
                    item.setPreco(itemDTO.getPreco());
                    item.setPedido(pedido);

                    pedido.getItens().add(item);
                }
            }

            // Pagamentos
            if (dto.getPagamentos() != null) {
                for (PagamentoDTO pagamentoDTO : dto.getPagamentos()) {
                    Pagamento pagamento = new Pagamento();
                    pagamento.setTipo(pagamentoDTO.getTipo());
                    pagamento.setParcelas(pagamentoDTO.getParcelas());
                    pagamento.setValor(pagamentoDTO.getValor());
                    pagamento.setPedido(pedido); // vínculo bidirecional

                    pedido.getPagamentos().add(pagamento);
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

        // Atualiza itens
        pedido.getItens().clear();
        if (dto.getItens() != null) {
            for (ItemPedidoDTO itemDTO : dto.getItens()) {
                Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                        .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + itemDTO.getProdutoId()));

                ItemPedido item = new ItemPedido();
                item.setProduto(produto);
                item.setQuantidade(itemDTO.getQuantidade());
                item.setPreco(itemDTO.getPreco());
                item.setPedido(pedido);

                pedido.getItens().add(item);
            }
        }

        // Atualiza pagamentos
        pedido.getPagamentos().clear(); // Remove todos os antigos
        if (dto.getPagamentos() != null) {
            for (PagamentoDTO pagamentoDTO : dto.getPagamentos()) {
                Pagamento pagamento = new Pagamento();
                pagamento.setTipo(pagamentoDTO.getTipo());
                pagamento.setParcelas(pagamentoDTO.getParcelas());
                pagamento.setValor(pagamentoDTO.getValor());
                pagamento.setPedido(pedido);

                pedido.getPagamentos().add(pagamento);
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

    public void removerItem(Long pedidoId, Long itemId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com id: " + pedidoId));

        ItemPedido item = itemPedidoRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com id: " + itemId));

        if (!item.getPedido().getId().equals(pedidoId)) {
            throw new DatabaseException("Item não pertence ao pedido informado.");
        }

        Produto produto = item.getProduto();
        if (produto.getEstoque() != null) {
            produto.setEstoque(produto.getEstoque() + item.getQuantidade());
            produtoRepository.save(produto);
        }

        pedido.getItens().remove(item);
        itemPedidoRepository.delete(item);
        pedidoRepository.save(pedido);
    }
}