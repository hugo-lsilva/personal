package br.com.companhia.vendas.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.companhia.vendas.domain.entity.AbstractEntity;
import br.com.companhia.vendas.infrastructure.db.H2JDBCUtils;

public abstract class AbstractDAO<T extends AbstractEntity> {
	/**
	 * Busca o registro pelo ID da entidade.
	 * @return
	 * @throws SQLException
	 */
	public abstract T findById(Integer id) throws SQLException;

	/**
	 * Retorna todos os registros da entidade.
	 * @return
	 * @throws SQLException
	 */
	public abstract List<T> findAll() throws SQLException;

	/**
	 * Grava novo registro da entidade.
	 * @param entity
	 * @return
	 * @throws SQLException
	 */
	public abstract int insert(T entity) throws SQLException;

	/**
	 * Altera registro da entidade.
	 * @param entity
	 * @return
	 * @throws SQLException
	 */
	public abstract int update(T entity) throws SQLException;

	/**
	 * Exclui o registro da entidade.
	 * @param entity
	 * @return
	 * @throws SQLException
	 */
	public abstract int delete(T entity) throws SQLException;

	/**
	 * Retorna conexão com o banco de dados.
	 * @return
	 * @throws SQLException
	 */
	protected Connection getConnection() throws SQLException {
		return H2JDBCUtils.getConnection();
	}
}