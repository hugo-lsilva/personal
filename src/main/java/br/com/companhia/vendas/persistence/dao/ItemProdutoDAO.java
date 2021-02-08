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

	/**
	 * Consulta item de produto pelo nome.
	 * 
	 * @param nome
	 * @return
	 * @throws SQLException
	 */
	public List<ItemProduto> findForName(String nome) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select item.codigoitem as coditem, item.nome as nomeitem, item.descricao as descitem, item.categoria catitem, item.codigoproduto as codprod from ITEM_PRODUTO item where upper(item.nome) like upper(?) order by item.nome ");
		Connection con = this.getConnection();
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, "%" + nome + "%");
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
	
	/**
	 * Consulta itens de produtos com o valor informado em todos os seus campos.
	 * 
	 * @param value
	 * @return
	 * @throws SQLException
	 */
	public List<ItemProduto> find(String value) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select item.codigoitem as coditem, item.nome as nomeitem, item.descricao as descitem, item.categoria catitem, item.codigoproduto as codprod from ITEM_PRODUTO item ");
		sql.append("where upper(item.nome) like upper(?) ");
		sql.append("or upper(item.descricao) like upper(?) ");
		sql.append("or upper(item.categoria) like upper(?) ");
		sql.append("order by item.codigoproduto, item.codigoitem ");
		Connection con = this.getConnection();
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, "%" + value + "%");
		ps.setString(2, "%" + value + "%");
		ps.setString(3, "%" + value + "%");
		
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

}