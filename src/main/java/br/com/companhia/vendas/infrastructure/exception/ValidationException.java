package br.com.companhia.vendas.infrastructure.exception;

/**
 * Classe que encapsula exceções negociais.
 * @author Hugo
 */
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 2095234029364129251L;

	public ValidationException(String message) {
		super(message);
	}
	
}
