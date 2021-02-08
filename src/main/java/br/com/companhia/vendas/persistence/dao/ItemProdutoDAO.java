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
 * Classe DAO com a lógica de persistência de dados da entidade ItemProduto.
 * @author Hugo
 */
@Named
public class ItemProdutoDAO extends AbstractDAO<ItemProduto> {
	@Override
	public ItemProduto findById(Integer id) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select item.codigoitem as coditem, item.nome as nomeitem, item.descricao as descitem, item.categoria catitem, ");
		sql.append("produto.codigoproduto as codprod, produto.nome as nomeprod, produto.descricao as descprod, produto.categoria as catprod ");
		sql.append("from ITEM_PRODUTO item inner join PRODUTO produto on produto.codigoproduto = item.codigoproduto where item.codigoitem = ? ");
		Connection con = this.getConnection();
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		ItemProduto item = new ItemProduto();
		if (rs.next()) {
			item.setCodigoItem(rs.getInt("coditem"));
			item.setNome(rs.getString("nomeitem"));
			item.setDescricao(rs.getString("descitem"));
			item.setCategoria(rs.getString("catitem"));
			Produto produto = new Produto();
			produto.setCodigoProduto(rs.getInt("codprod"));
			produto.setNome(rs.getString("nomeprod"));
			produto.setDescricao(rs.getString("descprod"));
			produto.setCategoria(rs.getString("catprod"));
			item.setProduto(produto);
		}
		ps.close();
		con.close();
		rs.close();
		return item;
	}

	@Override
	public List<ItemProduto> findAll() throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select item.codigoitem as coditem, item.nome as nomeitem, item.descricao as descitem, item.categoria catitem, item.codigoproduto as codprod from ITEM_PRODUTO item order by codigoproduto, codigoitem ");
		Connection con = this.getConnection();
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		List<ItemProduto> listaItens = new ArrayList<ItemProduto>();
		while (rs.next()) {
			ItemProduto item = new ItemProduto();
			item.setCodigoItem(rs.getInt(1));
			item.setNome(rs.getString(2));
			item.setDescricao(rs.getString(3));
			item.setCategoria(rs.getString(4));
			item.setProduto(new Produto(rs.getInt(5)));
			listaItens.add(item);
		}
		ps.close();
		con.close();
		rs.close();
		return listaItens;
	}

	@Override
	public int insert(ItemProduto item) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ITEM_PRODUTO (CODIGOPRODUTO, NOME, DESCRICAO, CATEGORIA) values (?, ?, ?, ?);");
		Connection con = this.getConnection();
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setInt(1, item.getProduto().getCodigoProduto());
		ps.setString(2, item.getNome());
		ps.setString(3, item.getDescricao());
		ps.setString(4, item.getCategoria());
		int retorno = ps.executeUpdate();
		con.commit();
		ps.close();
		con.close();
		return retorno;
	}

	@Override
	public int update(ItemProduto item) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("update ITEM_PRODUTO set NOME=?, DESCRICAO=?, CATEGORIA=? where CODIGOITEM = ?");
		Connection con = this.getConnection();
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, item.getNome());
		ps.setString(2, item.getDescricao());
		ps.setString(3, item.getCategoria());
		ps.setInt(4, item.getCodigoItem());
		int retorno = ps.executeUpdate();
		con.commit();
		ps.close();
		con.close();
		return retorno;
	}

	@Override
	public int delete(ItemProduto item) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from ITEM_PRODUTO where CODIGOITEM = ?");
		Connection con = this.getConnection();
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setInt(1, item.getCodigoItem());
		int retorno = ps.executeUpdate();
		con.commit();
		ps.close();
		con.close();
		return retorno;
	}
}