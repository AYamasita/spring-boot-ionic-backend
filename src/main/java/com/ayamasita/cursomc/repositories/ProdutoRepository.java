package com.ayamasita.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayamasita.cursomc.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto,Integer> {

}
