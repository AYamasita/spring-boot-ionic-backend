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
import com.ayamasita.cursomc.domain.Cliente;
import com.ayamasita.cursomc.dto.ClienteDTO;
import com.ayamasita.cursomc.repositories.ClienteRepository;
import com.ayamasita.cursomc.services.exceptions.DataIntegrityException;
import com.ayamasita.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired //automaticamente instanciada - injecao de dep. ou inversao controle
	private ClienteRepository repo;
	
	public Cliente find(Integer id)
	{		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() 
				-> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));	
		
	}

	public Cliente FromDTO( ClienteDTO objDTO) {
		// TODO Auto-generated method stub
		return new Cliente(objDTO.getId(),objDTO.getNome(),objDTO.getEmail(),null,null);
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());		
		updateData(obj, newObj);
		return repo.save(newObj);
	}
	
	private void updateData(Cliente obj, Cliente newObj) {
		// TODO Auto-generated method stub
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	}

	public void delete(Integer id)
	{
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			throw new DataIntegrityException("Deleting a client that has order is not allowed.");
		}	
	}
	
	public List<Cliente> findAll()
	{
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linerPerPage, String orderBy, String direction)
	{
		PageRequest pageRequest = PageRequest.of(page, linerPerPage,Direction.valueOf(direction),orderBy);
		return repo.findAll(pageRequest);
	}

}
