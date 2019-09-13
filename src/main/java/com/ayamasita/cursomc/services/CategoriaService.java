package com.ayamasita.cursomc.services;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ayamasita.cursomc.domain.Categoria;
import com.ayamasita.cursomc.repositories.CategoriaRepository;
import com.ayamasita.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class CategoriaService {
	
	@Autowired //automaticamente instanciada - injecao de dep. ou inversao controle
	private CategoriaRepository repo;	
	
	public Categoria find(Integer id) {
	
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() 
				-> new ObjectNotFoundException("Object not found! Id: " + id + ", Type: " + Categoria.class.getName()));	
	}		
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);		
	}
	
	public Categoria update(Categoria obj)
	{
		find(obj.getId());	
		return repo.save(obj);
		
	}
}
