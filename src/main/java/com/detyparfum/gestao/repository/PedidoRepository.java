package com.detyparfum.gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.detyparfum.gestao.entities.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
}
