package com.ayamasita.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ayamasita.cursomc.domain.Cliente;
import com.ayamasita.cursomc.dto.ClienteDTO;
import com.ayamasita.cursomc.dto.ClienteNewDTO;
import com.ayamasita.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

	@Autowired //instancia automatica
	private ClienteService service;
	
	@RequestMapping(value ="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		
		Cliente obj =  service.find(id);		
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDTO){
		Cliente obj =  service.FromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value ="/{id}",method = RequestMethod.PUT)
	public ResponseEntity<Cliente> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id)
	{
		Cliente obj = service.FromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	
	}

	@RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id)	
	{
		service.delete(id);
		return ResponseEntity.noContent().build();
		
	}
	
	//listar todos os clientes
	@RequestMapping(method =RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll()
	{
		List<Cliente>lista = service.findAll();		
		List<ClienteDTO> listaDTO = lista.stream().map(obj-> new ClienteDTO(obj)).collect(Collectors.toList());		
		return ResponseEntity.ok().body(listaDTO);
		
	}
	
	//listar os clientes por paginas
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page",defaultValue = "0") Integer page,
			@RequestParam(value="linerPerPage",defaultValue="24") Integer linerPerPage,
			@RequestParam(value="orderBy",defaultValue="nome") String orderBy, 
			@RequestParam(value="direction",defaultValue="ASC")String direction
			)
	{
		Page<Cliente> lista = service.findPage(page, linerPerPage, orderBy, direction);
		Page<ClienteDTO> listaDTO = lista.map(obj-> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listaDTO);
	}
	
	
}
