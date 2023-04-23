package com.levythalia.application.exception;

public class ErroSolicitacaoCartaoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ErroSolicitacaoCartaoException(String message) {
		super(message);
	}
}
