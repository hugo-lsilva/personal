package br.com.companhia.vendas.infrastructure.exception;

import java.sql.SQLException;

/**
 * Classe que encapsula exceções retornadas pela camada de persistência.
 * @author Hugo
 */
public class InfrastructureException extends Exception {

	private static final long serialVersionUID = -1650495146585771812L;

	public InfrastructureException(SQLException e) {
		super(e);
	}
}