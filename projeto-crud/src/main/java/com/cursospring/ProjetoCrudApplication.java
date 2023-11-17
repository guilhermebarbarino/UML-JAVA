package com.cursospring;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cursospring.domain.Categoria;
import com.cursospring.domain.Cidade;
import com.cursospring.domain.Cliente;
import com.cursospring.domain.Endereco;
import com.cursospring.domain.Estado;
import com.cursospring.domain.ItemPedido;
import com.cursospring.domain.Pagamento;
import com.cursospring.domain.PagamentoComBoleto;
import com.cursospring.domain.PagamentoComCartao;
import com.cursospring.domain.Pedido;
import com.cursospring.domain.Produto;
import com.cursospring.domain.enums.EstadoPagamento;
import com.cursospring.domain.enums.TipoCliente;
import com.cursospring.repositories.CategoriaRepository;
import com.cursospring.repositories.CidadeRepository;
import com.cursospring.repositories.ClienteRepository;
import com.cursospring.repositories.EnderecoRepository;
import com.cursospring.repositories.EstadoRepository;
import com.cursospring.repositories.ItemPedidoRepository;
import com.cursospring.repositories.PagamentoRepository;
import com.cursospring.repositories.PedidoRepository;
import com.cursospring.repositories.ProdutoRepository;

@SpringBootApplication
public class ProjetoCrudApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
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
	
	public static void main(String[] args) {
		SpringApplication.run(ProjetoCrudApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		/**
		 * script de instancias.
		 */
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 200.00);
		Produto p2 = new Produto(null, "Impressora", 900.00);
		Produto p3 = new Produto(null, "Mouse", 20.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p1));
		
		p1.getCategoria().addAll(Arrays.asList(cat1));
		p2.getCategoria().addAll(Arrays.asList(cat1, cat2));
		p3.getCategoria().addAll(Arrays.asList(cat1));
		
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2 , p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "são Paulo", est2);
		Cidade c3 = new Cidade(null, "campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva","maria@gmail.com","363638484",TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("2754548948","99898989"));
		
		Endereco e1 = new Endereco(null,"Rua Flores","300","apto 303","Jardim", "3686548", cli1, c1);
		Endereco e2 = new Endereco(null,"Avenida matos","100","apto 400","Centro", "5586548", cli1, c2);
		
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2023 10:00"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/05/2023 10:00"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1,6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2,sdf.parse("20/09/2023 10:00"),null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 800.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 8000.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip1));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

	}

}
