package com.levythalia.application.exception;

public class ErroComunicacaoMicroservicesException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private Integer status;

	public ErroComunicacaoMicroservicesException(String msg, Integer status) {
		super(msg);
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}
}
