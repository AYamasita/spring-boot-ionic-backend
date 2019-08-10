package com.ayamasita.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayamasita.cursomc.domain.Categoria;
import com.ayamasita.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired 
	private CategoriaRepository repo;
	
	//automaticamente instanciada - injecao de dep. ou inversao controle
	public Categoria buscar(Integer id) {
	
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
	
	}
	
}
