package br.com.companhia.vendas.main;

import java.util.List;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;

import br.com.companhia.vendas.domain.entity.ItemProduto;
import br.com.companhia.vendas.domain.entity.Produto;
import br.com.companhia.vendas.infrastructure.db.H2JDBCUtils;
import br.com.companhia.vendas.infrastructure.exception.InfrastructureException;
import br.com.companhia.vendas.infrastructure.exception.ValidationException;
import br.com.companhia.vendas.persistence.facade.RepositoryFacade;

/**
 * Classe para execução da aplicação.
 * @author Hugo
 */
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
		try { 
			// Executa os metodos de consultas para o ItemProduto
			runItemSearches();
			// Executa os metodos de consultas para o Produto
			runProdutoSearches();
		} catch (ValidationException ve) {
			// Se alguma validação de negócio não for satisfeita, exibe a mensagem de erro.
			System.out.println(ve.getMessage());
		}
	}

	private void runItemSearches() throws InfrastructureException {
		System.out.println("\n\n########### ITEM_PRODUTO CONSULTAS ###########");
		System.out.println("\n\n****** Buscar item de produto por ID *******");
		ItemProduto item = repository.buscaItemProdutoPorCodigo(1);
		System.out.println(item);
		System.out.println("\n\n****** Buscar todos os itens de produto (ordenados por produto) *******");
		List<ItemProduto> listaItens = repository.buscarTodosItemProdutos();
		listaItens.forEach(System.out::println);
		System.out.println("\n\n****** Buscar itens de produto por nome *******");
		repository.buscarItemProdutoPorNome("nvidia").forEach(System.out::println);
		System.out.println("\n\n****** Buscar itens de produtos em todos os campos *******");
		repository.buscarItemProduto("cc").forEach(System.out::println);
	}

	private void runProdutoSearches() throws InfrastructureException {
		System.out.println("\n\n########### PRODUTO CONSULTAS ###########");
		System.out.println("\n\n****** Buscar produto por ID *******");
		Produto produto = repository.buscaProdutoPorCodigo(1);
		System.out.println(produto);
		System.out.println("\n\n****** Buscar todos os produtos *******");
		List<Produto> listaProdutos = repository.buscarTodosProdutos();
		listaProdutos.forEach(System.out::println);
		System.out.println("\n\n****** Buscar produtos por nome *******");
		repository.buscarProdutoPorNome("vivo").forEach(System.out::println);
		System.out.println("\n\n****** Buscar produtos em todos os campos *******");
		repository.buscarProduto("aaa").forEach(System.out::println);
	}
}