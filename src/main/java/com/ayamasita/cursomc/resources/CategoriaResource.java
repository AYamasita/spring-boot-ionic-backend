

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

import com.ayamasita.cursomc.domain.Categoria;
import com.ayamasita.cursomc.dto.CategoriaDTO;
import com.ayamasita.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired  //instancia automaticamente
	private CategoriaService service;
	
	
	@RequestMapping(value ="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id){
			
		Categoria obj = service.find(id);			
		return ResponseEntity.ok().body(obj);		
	}	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO){
	    Categoria obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("{/id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody  CategoriaDTO objDTO , @PathVariable Integer id )
	{
		Categoria obj = service.fromDTO(objDTO);		
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
	
	@RequestMapping(method =RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll()	
	{
		List<Categoria> lista= service.findAll();
		List<CategoriaDTO> listaDTO = lista.stream().map(
				    obj -> new CategoriaDTO(obj)).collect(Collectors.toList()
				    		);
		return ResponseEntity.ok().body(listaDTO);	
	}
	
	@RequestMapping(value = "/page",method =RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page",defaultValue = "0") Integer page,
			@RequestParam(value="linerPerPage",defaultValue="24") Integer linerPerPage,
			@RequestParam(value="orderBy",defaultValue="nome") String orderBy, 
			@RequestParam(value="direction",defaultValue="ASC")String direction)	
	{
		Page<Categoria> lista = service.findPage(page, linerPerPage, orderBy, direction);
		Page<CategoriaDTO> listaDTO = lista.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listaDTO);	
	}
	
/*	
	@RequestMapping(method = RequestMethod.GET)
	public List<Categoria> listar() {
		
		Categoria cat1 = new Categoria(1,"Informatica");
		Categoria cat2 = new Categoria(2,"Escritorio");
		
		List<Categoria> lista = new ArrayList<Categoria>();
		lista.add(cat1);
		lista.add(cat2);
		
		return lista;
	}
	**/
}
