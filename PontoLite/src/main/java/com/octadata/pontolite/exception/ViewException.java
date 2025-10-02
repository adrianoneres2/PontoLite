package com.octadata.pontolite.exception;

import org.springframework.ui.Model;

public class ViewException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private final String code;
	private final String message;
	private Model model;
	
	public ViewException(String code,String message, Model model) {
		super(message);
		this.code = code;
		this.message = message;
		this.model = model;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Model getModel() {
		return model;
	}
}
