package br.customer.api.exceptions;

public class InternalServerException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3666293735919981396L;

	public InternalServerException(String message) {
		super(message);
	}
	
	public InternalServerException(String message, Throwable cause) {
		super(message, cause);
	}
}
