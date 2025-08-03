package com.octadata.pontolite.base;

public class Message {
	
	private String code;
	private String message;
	private String detail;
	
	public Message() {}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public Message getDefaultSuccess() {
		this.setCode(EnumMessage.SUCCESS.toString());
		this.setMessage("Registro processando com sucesso!");
		return this;
	}
	
	public Message getDefaultError() {
		this.setCode(EnumMessage.ERROR.toString());
		this.setMessage("Algo deu errado!");
		return this;
	}
}
