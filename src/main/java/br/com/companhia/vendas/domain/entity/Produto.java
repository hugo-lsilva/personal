package br.com.companhia.vendas.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class Produto implements AbstractEntity {
	private static final long serialVersionUID = 4159340449283733652L;
	private Integer codigoProduto;
	private String nome;
	private String descricao;
	private String categoria;
	private List<ItemProduto> itensProduto;

	public Produto() {
		super();
	}

	public Produto(Integer codigoProduto) {
		super();
		this.codigoProduto = codigoProduto;
	}

	public Produto(Integer codigoProduto, String nome, String descricao, String categoria) {
		super();
		this.codigoProduto = codigoProduto;
		this.nome = nome;
		this.descricao = descricao;
		this.categoria = categoria;
	}

	public Integer getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(Integer codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public List<ItemProduto> getItensProduto() {
		return itensProduto;
	}

	public void setItensProduto(List<ItemProduto> itensProduto) {
		this.itensProduto = itensProduto;
	}

	public void addItem(ItemProduto item) {
		if (this.getItensProduto() == null) {
			this.itensProduto = new ArrayList<ItemProduto>();
		}
		this.itensProduto.add(item);
	}

	@Override
	public String toString() {
		return "Produto [codigoProduto=" + codigoProduto + ", nome=" + nome + ", descricao=" + descricao
				+ ", categoria=" + categoria + ", itensProduto=" + itensProduto + "]";
	}
}