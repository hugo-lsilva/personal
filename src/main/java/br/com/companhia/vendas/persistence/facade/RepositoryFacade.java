package br.com.companhia.vendas.persistence.facade;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.companhia.vendas.domain.entity.ItemProduto;
import br.com.companhia.vendas.domain.entity.Produto;
import br.com.companhia.vendas.infrastructure.exception.InfrastructureException;
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

	public int salvaProduto(Produto produto) throws InfrastructureException {
		try {
			if (produto.getCodigoProduto() == null) {
				return this.produtoDAO.insert(produto);
			}
			return this.produtoDAO.update(produto);
		} catch (SQLException e) {
			throw new InfrastructureException(e);
		}
	}

	public int excluiProduto(Produto produto) throws InfrastructureException {
		try {
			return this.produtoDAO.delete(produto);
		} catch (SQLException e) {
			throw new InfrastructureException(e);
		}
	}

	public ItemProduto buscaItemProdutoPorCodigo(Integer id) throws InfrastructureException {
		try {
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

	public int salvaItemProduto(ItemProduto item) throws InfrastructureException {
		try {
			if (item.getCodigoItem() == null) {
				return this.itemProdutoDAO.insert(item);
			}
			return this.itemProdutoDAO.update(item);
		} catch (SQLException e) {
			throw new InfrastructureException(e);
		}
	}

	public int excluiItemProduto(ItemProduto item) throws InfrastructureException {
		try {
			return this.itemProdutoDAO.delete(item);
		} catch (SQLException e) {
			throw new InfrastructureException(e);
		}
	}
}