package com.ayamasita.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ayamasita.cursomc.domain.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {

}
