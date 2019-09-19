package com.ayamasita.cursomc;


import java.text.SimpleDateFormat;
import java.util.Arrays;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ayamasita.cursomc.domain.Categoria;
import com.ayamasita.cursomc.domain.Cidade;
import com.ayamasita.cursomc.domain.Cliente;
import com.ayamasita.cursomc.domain.Endereco;
import com.ayamasita.cursomc.domain.Estado;
import com.ayamasita.cursomc.domain.ItemPedido;
import com.ayamasita.cursomc.domain.Pagamento;
import com.ayamasita.cursomc.domain.PagamentoComBoleto;
import com.ayamasita.cursomc.domain.PagamentoComCartao;
import com.ayamasita.cursomc.domain.Pedido;
import com.ayamasita.cursomc.domain.Produto;
import com.ayamasita.cursomc.domain.enums.EstadoPagamento;
import com.ayamasita.cursomc.domain.enums.TipoCliente;
import com.ayamasita.cursomc.repositories.CategoriaRepository;
import com.ayamasita.cursomc.repositories.CidadeRepository;
import com.ayamasita.cursomc.repositories.ClienteRepository;
import com.ayamasita.cursomc.repositories.EnderecoRepository;
import com.ayamasita.cursomc.repositories.EstadoRepository;
import com.ayamasita.cursomc.repositories.ItemPedidoRepository;
import com.ayamasita.cursomc.repositories.PagamentoRepository;
import com.ayamasita.cursomc.repositories.PedidoRepository;
import com.ayamasita.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	//CommandLineRunner -> utilizado para executar no momento que estiver iniciando
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired	
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	@Override
	public void run(String... args) throws Exception
	{
		// TODO Auto-generated method stub
		Categoria cat1 = new Categoria(null,"Informatica");
		Categoria cat2 = new Categoria(null,"Escritorio");
		Categoria cat3 = new Categoria(null,"Cama mesa e banho");
		Categoria cat4 = new Categoria(null,"Eletrônicos");
		Categoria cat5 = new Categoria(null,"Jardinagem");
		Categoria cat6 = new Categoria(null,"Decoração");
		Categoria cat7 = new Categoria(null,"Perfumaria");
		
		Produto p1 = new Produto(null, "Computador",2000.00);
		Produto p2 = new Produto(null, "Impressora",800.00);
		Produto p3 = new Produto(null, "Mouse",80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7));		
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		//----
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade cid1 = new Cidade(null,"Uberlandia",est1);
		Cidade cid2 = new Cidade(null,"São Paulo",est2);
		Cidade cid3 = new Cidade(null,"Campinas",est2);		
		
		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2,cid3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(cid1,cid2,cid3));
		
		//----
		
		Cliente cli1 = new Cliente(null,"Maria Silva","maria@gmail.com","36378912377",TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));
		
		Endereco end1 = new Endereco(null,"Rua Flores","300","Apto 203","Jardim","38220834",cli1, cid1);
		Endereco end2 = new Endereco(null,"Avenida Matos","105","Sala 800","Centro","38777012",cli1,cid2);
		
		cli1.getEnderecos().addAll(Arrays.asList(end1,end2));	
			
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(end1,end2));
		
		//----
			
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		Pedido ped1 = new Pedido(null,sdf.parse("05/05/2017 10:32:00"),cli1,end1);
		Pedido ped2 = new Pedido(null,sdf.parse("10/10/2017 19:35:00"),cli1,end2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("11/11/2017 00:00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
	    pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
		
		//----
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 1, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 2, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		
		}

}
