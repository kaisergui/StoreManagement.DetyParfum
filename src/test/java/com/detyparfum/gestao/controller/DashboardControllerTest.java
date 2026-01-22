package com.detyparfum.gestao.controller;

import static org.hamcrest.Matchers.closeTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.detyparfum.gestao.entities.Cliente;
import com.detyparfum.gestao.entities.ItemPedido;
import com.detyparfum.gestao.entities.Pedido;
import com.detyparfum.gestao.entities.Produto;
import com.detyparfum.gestao.entities.enums.StatusPedido;
import com.detyparfum.gestao.repository.ClienteRepository;
import com.detyparfum.gestao.repository.PedidoRepository;
import com.detyparfum.gestao.repository.ProdutoRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @BeforeEach
    void setUp() {
        pedidoRepository.deleteAll();
        clienteRepository.deleteAll();
        produtoRepository.deleteAll();

        Cliente cliente1 = new Cliente(null, "Ana", "11111111111", "9999-9999", "ana@example.com");
        Cliente cliente2 = new Cliente(null, "Bruno", "22222222222", "8888-8888", "bruno@example.com");
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);

        Produto produto1 = new Produto(null, "Perfume A", 50.0, 8, "Dety");
        Produto produto2 = new Produto(null, "Perfume B", 70.0, null, "Dety");
        produtoRepository.save(produto1);
        produtoRepository.save(produto2);

        Pedido pedidoPagoAtual = new Pedido(null, toDate(LocalDate.now()), StatusPedido.PAGO, "Pago atual");
        pedidoPagoAtual.setCliente(cliente1);
        ItemPedido itemPago = new ItemPedido(null, 2, 50.0);
        itemPago.setProduto(produto1);
        itemPago.setPedido(pedidoPagoAtual);
        pedidoPagoAtual.getItens().add(itemPago);

        Pedido pedidoPendenteAtual = new Pedido(null, toDate(LocalDate.now()), StatusPedido.AGUARDANDO_PAGAMENTO, "Pendente atual");
        pedidoPendenteAtual.setCliente(cliente2);
        ItemPedido itemPendente = new ItemPedido(null, 1, 30.0);
        itemPendente.setProduto(produto1);
        itemPendente.setPedido(pedidoPendenteAtual);
        pedidoPendenteAtual.getItens().add(itemPendente);

        Pedido pedidoPagoAnterior = new Pedido(null, toDate(LocalDate.now().minusMonths(1)), StatusPedido.PAGO, "Pago anterior");
        pedidoPagoAnterior.setCliente(cliente1);
        ItemPedido itemAnterior = new ItemPedido(null, 1, 70.0);
        itemAnterior.setProduto(produto2);
        itemAnterior.setPedido(pedidoPagoAnterior);
        pedidoPagoAnterior.getItens().add(itemAnterior);

        pedidoRepository.save(pedidoPagoAtual);
        pedidoRepository.save(pedidoPendenteAtual);
        pedidoRepository.save(pedidoPagoAnterior);
    }

    @Test
    @WithMockUser
    void deveRetornarKpisDoDashboard() throws Exception {
        mockMvc.perform(get("/api/dashboard/kpis"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalClientes").value(2))
            .andExpect(jsonPath("$.produtosEstoque").value(8))
            .andExpect(jsonPath("$.pedidosMes").value(2))
            .andExpect(jsonPath("$.vendasTotais").value(2))
            .andExpect(jsonPath("$.faturamentoMensal").value(closeTo(100.0, 0.01)));
    }

    private Date toDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
