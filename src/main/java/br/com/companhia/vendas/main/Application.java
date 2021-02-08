package br.com.companhia.vendas.main;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;

import br.com.companhia.vendas.domain.entity.ItemProduto;
import br.com.companhia.vendas.domain.entity.Produto;
import br.com.companhia.vendas.infrastructure.db.H2JDBCUtils;
import br.com.companhia.vendas.infrastructure.exception.InfrastructureException;
import br.com.companhia.vendas.persistence.facade.RepositoryFacade;

/**
 * Classe para execução da aplicação.
 * @author Hugo
 */
@ApplicationScoped
public class Application {
	@Inject
	private RepositoryFacade repository;

	public static void main(String[] args) throws InfrastructureException {
		try {
			// Inicia a configuracao e criacao do banco de dados em memória, com uma carga
			// inicial.
			H2JDBCUtils.configDatabase(true);
			// Inicializa o container CDI
			SeContainerInitializer containerInitializer = SeContainerInitializer.newInstance();
			try (SeContainer container = containerInitializer.initialize()) {
				Application application = container.select(Application.class).get();
				application.run();
			}
		} catch (Exception e) {
			System.out.println("Ocorreu um erro inesperado durante a execução do programa.");
			e.printStackTrace();
		}
	}

	public void run() throws InfrastructureException {
		// Executa os metodos de CRUD de ItemProduto
		runItemCrud();
		// Executa os metodos de CRUD de Produto
		runProdutoCrud();
	}

	private void runItemCrud() throws InfrastructureException {
		System.out.println("\n\n########### ITEM_PRODUTO CRUD ###########");
		System.out.println("\n\n****** Buscar item de produto por ID *******");
		ItemProduto item = repository.buscaItemProdutoPorCodigo(1);
		System.out.println(item);
		System.out.println("\n\n****** Buscar todos os itens de produto (ordenados por produto) *******");
		List<ItemProduto> listaItens = repository.buscarTodosItemProdutos();
		listaItens.forEach(System.out::println);
		System.out.println("\n\n****** Incluir novo item_produto *******");
		ItemProduto novoItem = new ItemProduto(null, new Produto(2), "NVIDIA GeForce GTX com 2GB", "Placa de vídeo GeForce GTX com 2GB.", "Placa de Vídeo");
		int resultadoInclusao = this.repository.salvaItemProduto(novoItem);
		System.out.println((resultadoInclusao > 0) ? "Registro incluído com sucesso!: " + novoItem : "Registro NÃO gravado!");
		System.out.println("\n\n****** Excluir um item_produto *******");
		System.out.println(item);
		repository.excluiItemProduto(item);
		System.out.println("\n\n****** Alterar item_produto *******");
		item = this.repository.buscaItemProdutoPorCodigo(6);
		System.out.println("Item_Produto a ser alterado: " + item);
		item.setNome("SSD 240GB nome ALTERADO");
		item.setDescricao("SSD 240GB desc ALTERADO");
		item.setCategoria("SSD");
		int resultadoAlteracao = this.repository.salvaItemProduto(item);
		System.out.println((resultadoAlteracao > 0) ? "Registro alterado com sucesso!: " + item : "Registro NÃO gravado!");
		System.out.println("\n\n****** Resultado final (ordenados por produto) *******");
		repository.buscarTodosItemProdutos().forEach(System.out::println);
	}

	private void runProdutoCrud() throws InfrastructureException {
		System.out.println("\n\n########### PRODUTO CRUD ###########");
		System.out.println("\n\n****** Buscar produto por ID *******");
		Produto produto = repository.buscaProdutoPorCodigo(1);
		System.out.println(produto);
		System.out.println("\n\n****** Buscar todos os produtos *******");
		List<Produto> listaProdutos = repository.buscarTodosProdutos();
		listaProdutos.forEach(System.out::println);
		System.out.println("\n\n****** Incluir novo produto *******");
		Produto novoProduto = new Produto(null, "Monitor LED 24\" Samsung ", "Monitor LED 24\" Samsung LC24f390 1920x1080 Full HD Curvo", "Monitor");
		int resultadoInclusao = this.repository.salvaProduto(novoProduto);
		System.out.println((resultadoInclusao > 0) ? "Registro incluído com sucesso!: " + novoProduto : "Registro NÃO gravado!");
		System.out.println("\n\n****** Excluir um produto *******");
		System.out.println(produto);
		repository.excluiProduto(produto);
		System.out.println("\n\n****** Alterar produto *******");
		produto = this.repository.buscaProdutoPorCodigo(2);
		System.out.println("Produto a ser alterado: " + produto);
		produto.setNome("Smart TV LG 55\"");
		produto.setDescricao("Smart TV LG 55 4K\", AirPlay, 4 entradas HDMI, 1 entrada USB");
		produto.setCategoria("Televisores");
		int resultadoAlteracao = this.repository.salvaProduto(produto);
		System.out.println((resultadoAlteracao > 0) ? "Registro alterado com sucesso!: " + produto : "Registro NÃO gravado!");
		System.out.println("\n\n****** Resultado final *******");
		repository.buscarTodosProdutos().forEach(System.out::println);
	}
}