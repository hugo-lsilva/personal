package br.com.companhia.vendas.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import br.com.companhia.vendas.domain.entity.ItemProduto;
import br.com.companhia.vendas.domain.entity.Produto;

/**
 * Classe DAO com a lógica de persistência de dados da entidade Produto.
 * @author Hugo
 */
@Named
public class ProdutoDAO extends AbstractDAO<Produto> {
	@Override
	public Produto findById(Integer id) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select produto.codigoproduto as codprod, produto.nome as nomeprod, produto.descricao as descprod, produto.categoria catprod, ");
		sql.append(" item.codigoitem as coditem, item.nome as nomeitem, item.descricao as descitem, item.categoria catitem ");
		sql.append("from PRODUTO produto inner join ITEM_PRODUTO item on item.codigoproduto = produto.codigoproduto where produto.codigoproduto = ? ");
		Connection con = this.getConnection();
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		Produto produto = new Produto();
		if (rs.next()) {
			produto.setCodigoProduto(rs.getInt("codprod"));
			produto.setNome(rs.getString("nomeprod"));
			produto.setDescricao(rs.getString("descprod"));
			produto.setCategoria(rs.getString("catprod"));
			do {
				// Como a consulta eh pelo codigo do produto, todos os itens retornados serao do mesmo produto
				ItemProduto item = new ItemProduto();
				item.setCodigoItem(rs.getInt("coditem"));
				item.setProduto(new Produto(produto.getCodigoProduto()));
				item.setNome(rs.getString("nomeitem"));
				item.setDescricao(rs.getString("descitem"));
				item.setCategoria(rs.getString("catitem"));
				produto.addItem(item);
			} while (rs.next());
		}
		ps.close();
		con.close();
		rs.close();
		return produto;
	}

	@Override
	public List<Produto> findAll() throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select produto.codigoproduto as codprod, produto.nome as nomeprod, produto.descricao as descprod, produto.categoria catprod from PRODUTO produto order by produto.codigoproduto ");
		Connection con = this.getConnection();
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		List<Produto> listaProdutos = new ArrayList<Produto>();
		while (rs.next()) {
			Produto produto = new Produto();
			produto.setCodigoProduto(rs.getInt(1));
			produto.setNome(rs.getString(2));
			produto.setDescricao(rs.getString(3));
			produto.setCategoria(rs.getString(4));
			listaProdutos.add(produto);
		}
		ps.close();
		con.close();
		rs.close();
		return listaProdutos;
	}
	
	public List<Produto> findForName(String nome) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select produto.* from PRODUTO produto where upper(produto.nome) like upper(?) order by produto.codigoproduto ");
		Connection con = this.getConnection();
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, "%" + nome + "%¨");
		ResultSet rs = ps.executeQuery();
		List<Produto> listaProdutos = new ArrayList<Produto>();
		while (rs.next()) {
			Produto produto = new Produto();
			produto.setCodigoProduto(rs.getInt(1));
			produto.setNome(rs.getString(2));
			produto.setDescricao(rs.getString(3));
			produto.setCategoria(rs.getString(4));
			listaProdutos.add(produto);
		}
		ps.close();
		con.close();
		rs.close();
		return listaProdutos;
	} 

	@Override
	public int insert(Produto produto) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into PRODUTO (NOME, DESCRICAO, CATEGORIA) values (?, ?, ?);");
		Connection con = this.getConnection();
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, produto.getNome());
		ps.setString(2, produto.getDescricao());
		ps.setString(3, produto.getCategoria());
		int retorno = ps.executeUpdate();
		con.commit();
		ps.close();
		con.close();
		return retorno;
	}

	@Override
	public int update(Produto produto) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("update PRODUTO set NOME=?, DESCRICAO=?, CATEGORIA=? where CODIGOPRODUTO = ?");
		Connection con = this.getConnection();
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, produto.getNome());
		ps.setString(2, produto.getDescricao());
		ps.setString(3, produto.getCategoria());
		ps.setInt(4, produto.getCodigoProduto());
		int retorno = ps.executeUpdate();
		con.commit();
		ps.close();
		con.close();
		return retorno;
	}

	@Override
	public int delete(Produto produto) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from ITEM_PRODUTO where CODIGOPRODUTO = ?;");
		sql.append("delete from PRODUTO where CODIGOPRODUTO = ?");
		Connection con = this.getConnection();
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setInt(1, produto.getCodigoProduto());
		ps.setInt(2, produto.getCodigoProduto());
		int retorno = ps.executeUpdate();
		con.commit();
		ps.close();
		con.close();
		return retorno;
	}
}