package com.ayamasita.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ayamasita.cursomc.domain.Pedido;
import com.ayamasita.cursomc.repositories.PedidoRepository;
import com.ayamasita.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired //automaticamente instanciada - injecao de dep. ou inversao controle
	private PedidoRepository repo;
	
	public Pedido find( Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() 
				-> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		
	}
	
}
