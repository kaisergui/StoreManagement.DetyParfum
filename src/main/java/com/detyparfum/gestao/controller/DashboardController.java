package com.detyparfum.gestao.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.detyparfum.gestao.repository.ClienteRepository;
import com.detyparfum.gestao.repository.PagamentoRepository;
import com.detyparfum.gestao.repository.PedidoRepository;
import com.detyparfum.gestao.repository.ProdutoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
@Tag(name = "Dashboard", description = "Indicadores e relatórios do painel de gestão.")
public class DashboardController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @GetMapping("/clientes-mais-pedidos")
    @Operation(summary = "Clientes com mais pedidos", description = "Retorna ranking de clientes por quantidade de pedidos.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de clientes e quantidade de pedidos",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Map.class))))
    })
    public List<Map<String, Object>> getClientesMaisPedidos() {
        Query query = entityManager.createQuery("""
            SELECT p.cliente.nome, COUNT(p.id) 
            FROM Pedido p 
            GROUP BY p.cliente.nome 
            ORDER BY COUNT(p.id) DESC
        """);
        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] row : resultList) {
            Map<String, Object> item = new HashMap<>();
            item.put("nome", row[0]);
            item.put("quantidade", row[1]);
            response.add(item);
        }
        return response;
    }

    @GetMapping("/produtos-mais-vendidos")
    @Operation(summary = "Produtos mais vendidos", description = "Retorna ranking de produtos por quantidade vendida.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de produtos e quantidades vendidas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Map.class))))
    })
    public List<Map<String, Object>> getProdutosMaisVendidos() {
        Query query = entityManager.createQuery("""
            SELECT i.produto.nome, SUM(i.quantidade) 
            FROM ItemPedido i 
            GROUP BY i.produto.nome 
            ORDER BY SUM(i.quantidade) DESC
        """);
        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] row : resultList) {
            Map<String, Object> item = new HashMap<>();
            item.put("nome", row[0]);
            item.put("quantidade", row[1]);
            response.add(item);
        }
        return response;
    }

    @GetMapping("/pagamentos-pendentes")
    @Operation(summary = "Pagamentos pendentes", description = "Retorna clientes com valores pendentes de pagamento.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de clientes e valores pendentes",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Map.class))))
    })
    public List<Map<String, Object>> getPagamentosPendentes() {
		Query query = entityManager.createQuery("""
				SELECT p.cliente.nome, SUM(i.preco * i.quantidade)
				FROM Pedido p
				JOIN p.itens i
				WHERE p.status = 'AGUARDANDO_PAGAMENTO'
				GROUP BY p.cliente.nome
				        """);
        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] row : resultList) {
            Map<String, Object> item = new HashMap<>();
            item.put("nome", row[0]);
            item.put("valor", row[1]);
            response.add(item);
        }
        return response;
    }
    
    
    @GetMapping("/faturamento-mensal")
    @Operation(summary = "Faturamento mensal", description = "Retorna o faturamento agrupado por mês.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de meses e faturamento",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Map.class))))
    })
    public List<Map<String, Object>> getFaturamentoMensal() {
        Query query = entityManager.createQuery("""
            SELECT 
                to_char(p.data, 'YYYY-MM'),
                SUM(i.preco * i.quantidade)
            FROM Pedido p
            JOIN ItemPedido i ON p.id = i.pedido.id
            WHERE p.status = 'PAGO'
            GROUP BY to_char(p.data, 'YYYY-MM')
            ORDER BY to_char(p.data, 'YYYY-MM')
        """);

        List<Object[]> resultList = query.getResultList();
        List<Map<String, Object>> response = new ArrayList<>();
        
        for (Object[] row : resultList) {
            Map<String, Object> item = new HashMap<>();
            item.put("nome", row[0]); // mês no formato "YYYY-MM"
            item.put("valor", row[1]); // faturamento
            response.add(item);
        }

        return response;
    }
    
    @GetMapping("/kpis")
    @Operation(summary = "KPIs do painel", description = "Retorna os principais indicadores do negócio.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mapa de KPIs",
            content = @Content(schema = @Schema(implementation = Map.class)))
    })
    public Map<String, Object> getKPIs() {
        Map<String, Object> kpis = new HashMap<>();

        // Total de Clientes
        Query totalClientesQuery = entityManager.createQuery("SELECT COUNT(c) FROM Cliente c");
        Long totalClientes = (Long) totalClientesQuery.getSingleResult();
        kpis.put("totalClientes", totalClientes);

        // Produtos em Estoque
        Query estoqueQuery = entityManager.createQuery("SELECT SUM(CASE WHEN p.estoque IS NULL THEN 0 ELSE p.estoque END) FROM Produto p");
        Long produtosEstoque = (Long) estoqueQuery.getSingleResult();
        kpis.put("produtosEstoque", produtosEstoque);

        // Pedidos do Mês atual
        LocalDate hoje = LocalDate.now();
        LocalDate inicioMes = hoje.withDayOfMonth(1);
        LocalDate proximoMes = inicioMes.plusMonths(1);
        Query pedidosMesQuery = entityManager.createQuery("""
            SELECT COUNT(p)
            FROM Pedido p
            WHERE p.data >= :inicioMes AND p.data < :proximoMes
        """);
        pedidosMesQuery.setParameter("inicioMes", inicioMes.atStartOfDay());
        pedidosMesQuery.setParameter("proximoMes", proximoMes.atStartOfDay());
        Long pedidosMes = (Long) pedidosMesQuery.getSingleResult();
        kpis.put("pedidosMes", pedidosMes);

        // Vendas Totais
        Query vendasTotaisQuery = entityManager.createQuery("""
            SELECT COUNT(p)
            FROM Pedido p
            WHERE p.status = 'PAGO'
        """);
        Long vendasTotais = (Long) vendasTotaisQuery.getSingleResult();
        kpis.put("vendasTotais", vendasTotais);

        // Faturamento Mensal
        Query faturamentoQuery = entityManager.createQuery("""
            SELECT SUM(i.preco * i.quantidade)
            FROM Pedido p
            JOIN p.itens i
            WHERE p.status = 'PAGO'
            AND p.data >= :inicioMes
            AND p.data < :proximoMes
        """);
        faturamentoQuery.setParameter("inicioMes", inicioMes.atStartOfDay());
        faturamentoQuery.setParameter("proximoMes", proximoMes.atStartOfDay());
        Object resultado = faturamentoQuery.getSingleResult();
        BigDecimal faturamentoMensal = resultado != null ? BigDecimal.valueOf((Double) resultado) : BigDecimal.ZERO;
        kpis.put("faturamentoMensal", faturamentoMensal);

        return kpis;
    }

    
}
