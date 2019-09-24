package com.ayamasita.cursomc.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ayamasita.cursomc.domain.Cidade;
import com.ayamasita.cursomc.domain.Cliente;
import com.ayamasita.cursomc.domain.Endereco;
import com.ayamasita.cursomc.domain.enums.TipoCliente;
import com.ayamasita.cursomc.dto.ClienteDTO;
import com.ayamasita.cursomc.dto.ClienteNewDTO;

import com.ayamasita.cursomc.repositories.ClienteRepository;
import com.ayamasita.cursomc.repositories.EnderecoRepository;
import com.ayamasita.cursomc.services.exceptions.DataIntegrityException;
import com.ayamasita.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired //automaticamente instanciada - injecao de dep. ou inversao controle
	private ClienteRepository repo;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	
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
	
	public Cliente FromDTO(ClienteNewDTO objDTO)
	{
		Cliente cli = new Cliente(null,objDTO.getNome(),objDTO.getEmail(),
				objDTO.getCpfOuCnpj(),TipoCliente.ToEnum(objDTO.getTipo()));
		
		Cidade cid = new Cidade(objDTO.getCidadeId(),null,null);
		
		Endereco end = new Endereco(null,objDTO.getLogradouro(),objDTO.getNumero(),objDTO.getLogradouro(),
				                    objDTO.getBairro(),objDTO.getCep(),
				                    cli,cid);
		cli.getEnderecos().add(end);
		
		cli.getTelefones().add(objDTO.getTelefone1());		
		if (objDTO.getTelefone2() != null)
			cli.getTelefones().add(objDTO.getTelefone2());
		if (objDTO.getTelefone3() != null)
			cli.getTelefones().add(objDTO.getTelefone3());
		
		return cli;
	}
	
	@Transactional
	public Cliente insert(Cliente obj)
	{
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());		
		return obj;
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
