package com.ayamasita.cursomc.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ayamasita.cursomc.domain.Categoria;
import com.ayamasita.cursomc.dto.CategoriaDTO;
import com.ayamasita.cursomc.repositories.CategoriaRepository;
import com.ayamasita.cursomc.services.exceptions.DataIntegrityException;
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
		Categoria  newObj = find(obj.getId());	
		UpdateData(newObj, obj);
		return repo.save(obj);		
	}
	
	private void UpdateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}

	public void delete(Integer id)
	{
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			 throw new DataIntegrityException("Deleting a category that has products is not allowed.");
		}
		
	}
	
	public List<Categoria> findAll()
	{
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linerPerPage, String orderBy, String direction)
	{
		PageRequest pageRequest = PageRequest.of(page, linerPerPage,Direction.valueOf(direction),orderBy);
		return repo.findAll(pageRequest);
	}
	
	public  Categoria fromDTO(CategoriaDTO objDTO)
	{
		return new Categoria(objDTO.getId(),objDTO.getNome());
	}
	
}
