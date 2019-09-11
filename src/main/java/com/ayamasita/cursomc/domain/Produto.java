package com.ayamasita.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Produto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Double preco;
	
	//mapeamento entre produtos e categorias que evitam a referencia ciclica(JsonBackReference)
	@JsonBackReference
	@ManyToMany
	@JoinTable(name= "PRODUTO_CATEGORIA",
			  joinColumns = @JoinColumn(name = "produto_id"),  //PK
			  inverseJoinColumns = @JoinColumn(name = "categoria_id"))  //PK	
	private List<Categoria> categorias = new ArrayList<Categoria>();
	
	@JsonIgnore //não é importante serializar a partira do produtos os itens de pedido.. mas sim do item pedido qual é o produto.
	@OneToMany(mappedBy = "id.produto")  //mapeamento da associação
	private Set<ItemPedido> itens = new HashSet<ItemPedido>();
	
	public Produto() {}

	public Produto(Integer id, String nome, Double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}
	
	//tudo que começa com o get.. o sistema identifica que precisa ser serializado.	
	//@JsonIgnore no lado da associação que não deve ser serializado
	@JsonIgnore
	public List<Pedido> getPedidos()
	{
		//Informa os pedidos que o produto possui.
		List<Pedido> lista = new ArrayList<Pedido>();
		for(ItemPedido x:  itens)
		{
			lista.add(x.getPedido());
		}
		return lista;
		
	}	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", preco=" + preco + "]";
	}


}
