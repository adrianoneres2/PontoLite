package com.octadata.pontolite.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.octadata.pontolite.dto.MessageHandlerDTO;
import com.octadata.pontolite.exception.NegocioException;

@ControllerAdvice
public class NegocioExceptionHandler {

	@ExceptionHandler(NegocioException.class)
	public MessageHandlerDTO handleNegocioException(NegocioException ex) {
		return new MessageHandlerDTO(ex.getCode(), ex.getMessage(), ex.getDetail());
	}
}