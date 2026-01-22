package com.detyparfum.gestao.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.detyparfum.gestao.dto.ClienteDTO;
import com.detyparfum.gestao.dto.ClienteDetalhesDTO;
import com.detyparfum.gestao.dto.ItemPedidoDetalhadoDTO;
import com.detyparfum.gestao.dto.PagamentoDTO;
import com.detyparfum.gestao.dto.PedidoDetalhadoDTO;
import com.detyparfum.gestao.entities.Cliente;
import com.detyparfum.gestao.entities.Pedido;
import com.detyparfum.gestao.exception.DatabaseException;
import com.detyparfum.gestao.exception.ResourceNotFoundException;
import com.detyparfum.gestao.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ClienteDTO salvar(ClienteDTO dto) {
        try {
            Cliente cliente = modelMapper.map(dto, Cliente.class);
            return modelMapper.map(clienteRepository.save(cliente), ClienteDTO.class);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(c -> modelMapper.map(c, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    public ClienteDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    public ClienteDTO atualizar(Long id, ClienteDTO dto) {
    	  Cliente cliente = clienteRepository.findById(id)
    	            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));

    	    cliente.setNome(dto.getNome());
    	    cliente.setCpf(dto.getCpf());
    	    cliente.setTelefone(dto.getTelefone());
    	    cliente.setEmail(dto.getEmail());

    	    cliente = clienteRepository.save(cliente);
    	    return modelMapper.map(cliente, ClienteDTO.class);
    }

    public void deletar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com id: " + id);
        }
        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao deletar cliente: dados relacionados impedem a exclusão.");
        }
    }
    
    public ClienteDetalhesDTO obterDetalhesCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));

        ClienteDetalhesDTO dto = new ClienteDetalhesDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setCpf(cliente.getCpf());
        dto.setTelefone(cliente.getTelefone());
        dto.setEmail(cliente.getEmail());

        List<PedidoDetalhadoDTO> pedidos = cliente.getPedidos().stream()
        		.sorted(Comparator.comparing(Pedido::getData).reversed())// ← ORDEM POR DATA
            .map(p -> {
                List<PagamentoDTO> pagamentoDTOs = p.getPagamentos().stream().map(pagamento ->
                    new PagamentoDTO(
                        pagamento.getId(),
                        pagamento.getTipo(),
                        pagamento.getParcelas(),
                        pagamento.getValor(),
                        pagamento.getPedido().getId()
                    )
                ).collect(Collectors.toList());

                List<ItemPedidoDetalhadoDTO> itens = p.getItens().stream().map(i ->
                    new ItemPedidoDetalhadoDTO(
                        i.getProduto() != null ? i.getProduto().getNome() : "Produto não identificado",
                        i.getPreco(),
                        i.getQuantidade()
                    )
                ).collect(Collectors.toList());

                PedidoDetalhadoDTO pedido = new PedidoDetalhadoDTO();
                pedido.setId(p.getId());
                pedido.setData(p.getData());
                pedido.setStatus(p.getStatus().toString());
                pedido.setObservacao(p.getObservacao());
                pedido.setPagamentos(pagamentoDTOs);
                pedido.setItens(itens);
                return pedido;
            }).collect(Collectors.toList());

        dto.setPedidos(pedidos);
        return dto;
    }
    
}
