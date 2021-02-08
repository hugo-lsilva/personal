package br.com.companhia.vendas.domain.entity;

public class ItemProduto implements AbstractEntity {
	private static final long serialVersionUID = -7877052375475254701L;
	private Integer codigoItem;
	private Produto produto;
	private String nome;
	private String descricao;
	private String categoria;

	public ItemProduto() {
	}

	public ItemProduto(Integer codigoItem, Produto produto) {
		super();
		this.codigoItem = codigoItem;
		this.produto = produto;
	}

	public ItemProduto(Integer codigoItem, Produto produto, String nome, String descricao, String categoria) {
		super();
		this.codigoItem = codigoItem;
		this.produto = produto;
		this.nome = nome;
		this.descricao = descricao;
		this.categoria = categoria;
	}

	public Integer getCodigoItem() {
		return codigoItem;
	}

	public void setCodigoItem(Integer codigoItem) {
		this.codigoItem = codigoItem;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
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

	@Override
	public String toString() {
		return "ItemProduto [codigoItem=" + codigoItem + ", nome=" + nome + ", descricao=" + descricao + ", categoria="
				+ categoria + ", produto=" + produto + "]";
	}
}