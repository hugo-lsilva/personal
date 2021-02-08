package br.com.companhia.vendas.infrastructure.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe utilitária para conexão com o banco de dados H2 (in memory).
 * @author Hugo
 */
public class H2JDBCUtils {
	private static String jdbcURL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	private static String jdbcUsername = "sa";
	private static String jdbcPassword = "";

	/**
	 * Cria e configura um banco de dados em memória.
	 * @param cargaInicial Se <i>TRUE</i>, é realizada uma carga inicial na base de dados.
	 */
	public static void configDatabase(boolean cargaInicial) {
		try {
			// Cria as tabelas no banco.
			createTables();
			if (cargaInicial) {
				// Faz a carga inicial nas tabelas
				loadTables();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retorna um objeto de conexão com o banco de dados.
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
	}

	/**
	 * Método de criação das tabelas em banco.
	 * @throws SQLException
	 */
	private static void createTables() throws SQLException {
		StringBuffer ddl = new StringBuffer("");
		// Criacao da tabela PRODUTO
		ddl.append("create table PRODUTO ( ");
		ddl.append(" CODIGOPRODUTO int primary key auto_increment, ");
		ddl.append(" NOME varchar, ");
		ddl.append(" DESCRICAO varchar, ");
		ddl.append(" CATEGORIA varchar ");
		ddl.append(" ); ").append("\n");
		// Criacao da tabela PRODUTO
		ddl.append("create table ITEM_PRODUTO ( ");
		ddl.append(" CODIGOPRODUTO int not null, ");
		ddl.append(" CODIGOITEM int auto_increment, ");
		ddl.append(" NOME varchar, ");
		ddl.append(" DESCRICAO varchar, ");
		ddl.append(" CATEGORIA varchar ");
		ddl.append(" ); ").append("\n");
		// Criacao das PK's e FK's.
		ddl.append("ALTER TABLE ITEM_PRODUTO ADD PRIMARY KEY (CODIGOITEM, CODIGOPRODUTO); ");
		ddl.append("ALTER TABLE ITEM_PRODUTO ");
		ddl.append("ADD FOREIGN KEY (CODIGOPRODUTO) ");
		ddl.append("REFERENCES PRODUTO(CODIGOPRODUTO); ").append("\n");
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(ddl.toString());
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	/**
	 * Método para carga inicial das tabelas.
	 * @throws SQLException
	 */
	private static void loadTables() throws SQLException {
		StringBuffer insert = new StringBuffer("");
		// Carga das tabelas
		// PRODUTO 1
		insert.append("insert into PRODUTO (CODIGOPRODUTO, NOME, DESCRICAO, CATEGORIA) values ");
		insert.append("(1, 'Notebook ASUS VivoBook 15', 'Notebook Asus VivoBook 15, Intel® CoreT i3 6100U, 4 GB, 1 TB, Tela de 15,6\", Cinza Escuro', 'Notebook pessoal');").append("\n");
		insert.append("insert into ITEM_PRODUTO (CODIGOPRODUTO, NOME, DESCRICAO, CATEGORIA) values ");
		insert.append("(1, 'Pente de memória RAM', 'Pente DDR4 de 4GB', 'Memória RAM');").append("\n");
		insert.append("insert into ITEM_PRODUTO (CODIGOPRODUTO, NOME, DESCRICAO, CATEGORIA) values ");
		insert.append("(1, 'Intel Core i3', 'Processador Intel Core i3.', 'Processador');").append("\n");
		insert.append("insert into ITEM_PRODUTO (CODIGOPRODUTO, NOME, DESCRICAO, CATEGORIA) values ");
		insert.append("(1, 'Intel HD Graphics 520', 'Placa de vídeo Intel HD Graphics 520.', 'Placa de Vídeo');").append("\n");
		insert.append("insert into ITEM_PRODUTO (CODIGOPRODUTO, NOME, DESCRICAO, CATEGORIA) values ");
		insert.append("(1, 'HD de 1TB', 'HD - 1TB Velocidade: SATA 5400 RPM.', 'Armazenamento');").append("\n");
		// PRODUTO 2
		insert.append("insert into PRODUTO (CODIGOPRODUTO, NOME, DESCRICAO, CATEGORIA) values ");
		insert.append("(2, 'Notebook Dell Inspiron 3540', 'Notebook Dell. Tela 14\", Processador Intel Core i5, 8 GB de RAM, SSD 240 GB. aaa', 'Notebook pessoal');").append("\n");
		insert.append("insert into ITEM_PRODUTO (CODIGOPRODUTO, NOME, DESCRICAO, CATEGORIA) values ");
		insert.append("(2, 'Pente de memória RAM', 'Pente DDR3 de 8GB', 'Memória RAM');").append("\n");
		insert.append("insert into ITEM_PRODUTO (CODIGOPRODUTO, NOME, DESCRICAO, CATEGORIA) values ");
		insert.append("(2, 'SSD 240GB', 'SSD 240GB.', 'Armazenamento');").append("\n");
		// PRODUTO 3
		insert.append("insert into PRODUTO (CODIGOPRODUTO, NOME, DESCRICAO, CATEGORIA) values ");
		insert.append("(3, 'Notebook Gamer Acer Aspire Nitro 5', 'Notebook Gamer Acer Aspire Nitro 5 AN515-54-574Q 9ª Intel Core i5 8GB (GeForce GTX1650 com 4GB) 512GB SSD 15.6\".', 'Gamer');").append("\n");
		insert.append("insert into ITEM_PRODUTO (CODIGOPRODUTO, NOME, DESCRICAO, CATEGORIA) values ");
		insert.append("(3, 'Processador Intel Core i5 8GB', 'Processador Intel Core i5 8GB', 'Processador');").append("\n");
		insert.append("insert into ITEM_PRODUTO (CODIGOPRODUTO, NOME, DESCRICAO, CATEGORIA) values ");
		insert.append("(3, 'NVIDIA GeForce GTX1650 com 4GB', 'Placa de vídeo GeForce GTX1650 com 4GB.cc', 'Placa de Vídeo');").append("\n");
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(insert.toString());
		} finally {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}
}