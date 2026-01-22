package com.detyparfum.gestao.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.detyparfum.gestao.dto.ItemPedidoDTO;
import com.detyparfum.gestao.dto.PagamentoDTO;
import com.detyparfum.gestao.dto.PedidoDTO;
import com.detyparfum.gestao.entities.Cliente;
import com.detyparfum.gestao.entities.Pedido;
import com.detyparfum.gestao.entities.Produto;
import com.detyparfum.gestao.entities.enums.StatusPedido;
import com.detyparfum.gestao.repository.ClienteRepository;
import com.detyparfum.gestao.repository.PedidoRepository;
import com.detyparfum.gestao.repository.ProdutoRepository;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PedidoServiceTest {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    private Cliente cliente;
    private Produto produto;

    @BeforeEach
    void setUp() {
        pedidoRepository.deleteAll();
        clienteRepository.deleteAll();
        produtoRepository.deleteAll();

        cliente = clienteRepository.save(new Cliente(null, "Maria", "12345678900", "9999-0000", "maria@example.com"));
        produto = produtoRepository.save(new Produto(null, "Perfume Teste", 30.0, 10, "Dety"));
    }

    @Test
    void deveAtualizarPagamentosSemDuplicar() {
        PedidoDTO novoPedido = new PedidoDTO();
        novoPedido.setClienteId(cliente.getId());
        novoPedido.setData(toDate(LocalDate.now()));
        novoPedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        novoPedido.setObservacao("Pedido inicial");
        novoPedido.setItens(List.of(new ItemPedidoDTO(null, 1, 30.0, produto.getId(), null)));
        novoPedido.setPagamentos(List.of(new PagamentoDTO(null, "DINHEIRO", 1, 20.0, null)));

        PedidoDTO salvo = pedidoService.salvar(novoPedido);

        PedidoDTO atualizacao = new PedidoDTO();
        atualizacao.setClienteId(cliente.getId());
        atualizacao.setData(salvo.getData());
        atualizacao.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        atualizacao.setObservacao("Atualizado");
        atualizacao.setItens(List.of(new ItemPedidoDTO(null, 2, 30.0, produto.getId(), null)));
        atualizacao.setPagamentos(List.of(
            new PagamentoDTO(null, "DINHEIRO", 1, 20.0, null),
            new PagamentoDTO(null, "DINHEIRO", 1, 20.0, null)
        ));

        pedidoService.atualizar(salvo.getId(), atualizacao);

        Pedido pedidoAtualizado = pedidoRepository.findById(salvo.getId()).orElseThrow();
        assertThat(pedidoAtualizado.getPagamentos()).hasSize(2);

        atualizacao.setPagamentos(List.of(new PagamentoDTO(null, "PIX", 1, 40.0, null)));
        pedidoService.atualizar(salvo.getId(), atualizacao);

        Pedido pedidoFinal = pedidoRepository.findById(salvo.getId()).orElseThrow();
        assertThat(pedidoFinal.getPagamentos()).hasSize(1);
        assertThat(pedidoFinal.getPagamentos().get(0).getTipo()).isEqualTo("PIX");
    }

    private Date toDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
