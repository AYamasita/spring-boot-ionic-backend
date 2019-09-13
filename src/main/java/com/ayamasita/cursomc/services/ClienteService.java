package com.ayamasita.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayamasita.cursomc.domain.Cliente;
import com.ayamasita.cursomc.repositories.ClienteRepository;
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

}
