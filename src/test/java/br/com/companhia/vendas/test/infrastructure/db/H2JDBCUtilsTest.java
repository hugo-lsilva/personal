package br.com.companhia.vendas.test.infrastructure.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import br.com.companhia.vendas.infrastructure.db.H2JDBCUtils;

/**
 * Classe de testes da classe H2JDBCUtils.
 * 
 * @author Hugo
 *
 */
public class H2JDBCUtilsTest {
	private static final String[] TABELAS = { "PRODUTO", "ITEM_PRODUTO" };
	static {
		try {
			// Starta a criacao da estrutura do banco de dados
			H2JDBCUtils.configDatabase(true);
		} catch (Exception e) {
			// Ignore
		}
	}

	/**
	 * Método de teste para a conexão com o banco de dados H2.
	 */
	@Test
	public void testH2Connection() {
		// Tenta estabelecer conexao com o banco
		try (var con = H2JDBCUtils.getConnection();
				var stmt = con.createStatement();
				var rs = stmt.executeQuery("SELECT 1+1")) {
			int result = 0;
			if (rs.next()) {
				result = rs.getInt(1);
			}
			// Valida se o resultado retornado corresponde ao valor esperado.
			assertEquals("Falha na conexão com o banco de dados H2.", 2, result);
			con.close();
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método de teste da criação da estrutura de tabelas do banco de dados. Se
	 * alguma das tabelas não existir, esta é apontada na falha do teste.
	 * 
	 */
	@Test
	public void testDBCreation() throws SQLException {
		// Estabelece conexao com o banco
		Connection con = H2JDBCUtils.getConnection();
		Statement stm = con.createStatement();
		for (String tabela : TABELAS) {
			try {
				// Executa uma consulta em cada uma das tabelas para verificar se esta existe.
				stm.executeQuery("select * from " + tabela);
			} catch (SQLException e) {
				assertTrue("Ocorreu uma falha na criação da tabela " + tabela + ".", false);
				e.printStackTrace();
			}
		}
		con.close();
		stm.close();
	}

	/**
	 * Método de teste para verificar se todas as tabelas foram carregadas.
	 * 
	 */
	@Test
	public void testTableInsert() throws SQLException {
		// Estabelece conexao com o banco
		Connection con = H2JDBCUtils.getConnection();
		Statement stm = con.createStatement();
		for (String tabela : TABELAS) {
			try {
				// Executa uma consulta em cada uma das tabelas para verificar se esta existe.
				ResultSet rs = stm.executeQuery("select count(*) from " + tabela);
				int quantidade = 0;
				if (rs.next()) {
					quantidade = rs.getInt(1);
				}
				assertTrue("Nenhum registro encontrado na tabela " + tabela + ".", quantidade > 0);
				rs.close();
			} catch (SQLException e) {
				assertTrue("Ocorreu uma falha na consulta à tabela " + tabela + ".", false);
				e.printStackTrace();
			}
		}
		con.close();
		stm.close();
	}
}