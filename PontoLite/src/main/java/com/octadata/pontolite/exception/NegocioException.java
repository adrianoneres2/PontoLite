package com.octadata.pontolite.exception;


public class NegocioException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private final String code;
	private final String detail;
	private final String message;
	
	public NegocioException(String code,String message, String detail) {
		super(message);
		this.code = code;
		this.detail = detail;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getDetail() {
		return detail;
	}

	public String getMessage() {
		return message;
	}

}
