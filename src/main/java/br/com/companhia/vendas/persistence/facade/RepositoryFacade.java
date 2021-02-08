package br.com.companhia.vendas.persistence.facade;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.companhia.vendas.domain.entity.ItemProduto;
import br.com.companhia.vendas.domain.entity.Produto;
import br.com.companhia.vendas.infrastructure.exception.InfrastructureException;
import br.com.companhia.vendas.infrastructure.exception.ValidationException;
import br.com.companhia.vendas.persistence.dao.ItemProdutoDAO;
import br.com.companhia.vendas.persistence.dao.ProdutoDAO;

/**
 * Classe que implementa a fachada para a camada de persistência.
 * @author Hugo
 */
@Named
public class RepositoryFacade {
	@Inject
	private ProdutoDAO produtoDAO;
	@Inject
	private ItemProdutoDAO itemProdutoDAO;

	public Produto buscaProdutoPorCodigo(Integer id) throws InfrastructureException {
		try {
			if (isEmpty(id)) {
				throw new ValidationException("Filtro obrigatório!");
			}
			return this.produtoDAO.findById(id);
		} catch (SQLException e) {
			throw new InfrastructureException(e);
		}
	}

	public List<Produto> buscarTodosProdutos() throws InfrastructureException {
		try {
			return this.produtoDAO.findAll();
		} catch (SQLException e) {
			throw new InfrastructureException(e);
		}
	}
	
	public List<Produto> buscarProdutoPorNome(String nome) throws InfrastructureException {
		try {
			if (isEmpty(nome)) {
				throw new ValidationException("Filtro obrigatório!");
			}
			return this.produtoDAO.findForName(nome);
		} catch (SQLException e) {
			throw new InfrastructureException(e);
		}
	}
	
	/**
	 * Consulta um produto pelo valor informado em todos os seus atributos.
	 * @param valor
	 * @return
	 * @throws InfrastructureException
	 */
	public List<Produto> buscarProduto(String valor) throws InfrastructureException {
		try {
			if (isEmpty(valor)) {
				throw new ValidationException("Filtro obrigatório!");
			}
			return this.produtoDAO.find(valor);
		} catch (SQLException e) {
			throw new InfrastructureException(e);
		}
	}

	public ItemProduto buscaItemProdutoPorCodigo(Integer id) throws InfrastructureException {
		try {
			if (isEmpty(id)) {
				throw new ValidationException("Filtro obrigatório!");
			}
			return this.itemProdutoDAO.findById(id);
		} catch (SQLException e) {
			throw new InfrastructureException(e);
		}
	}

	public List<ItemProduto> buscarTodosItemProdutos() throws InfrastructureException {
		try {
			return this.itemProdutoDAO.findAll();
		} catch (SQLException e) {
			throw new InfrastructureException(e);
		}
	}
	
	public List<ItemProduto> buscarItemProdutoPorNome(String nome) throws InfrastructureException {
		try {
			if (isEmpty(nome)) {
				throw new ValidationException("Filtro obrigatório!");
			}
			return this.itemProdutoDAO.findForName(nome);
		} catch (SQLException e) {
			throw new InfrastructureException(e);
		}
	}
	
	/**
	 * Consulta itens de produtos com o valor informado em todos os seus campos.
	 * @param valor
	 * @return
	 * @throws InfrastructureException
	 */
	public List<ItemProduto> buscarItemProduto(String valor) throws InfrastructureException {
		try {
			if (isEmpty(valor)) {
				throw new ValidationException("Filtro obrigatório!");
			}
			return this.itemProdutoDAO.find(valor);
		} catch (SQLException e) {
			throw new InfrastructureException(e);
		}
	}
	
	private static final boolean isEmpty(String valor) {
		return valor == null || valor.trim().isEmpty();
	}
	
	private static final boolean isEmpty(Object object) {
		return object == null;
	}

}