package com.detyparfum.gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.detyparfum.gestao.entities.Test;



public interface TestRepository extends JpaRepository<Test, Long> {
	
}
