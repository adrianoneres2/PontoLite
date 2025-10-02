package com.octadata.pontolite.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.octadata.pontolite.dto.MessageHandlerDto;
import com.octadata.pontolite.exception.NegocioException;

@ControllerAdvice
public class NegocioExceptionHandler {
	
	@ExceptionHandler(NegocioException.class)
	public MessageHandlerDto handleNegocioException(NegocioException ex) {
		return new MessageHandlerDto(ex.getCode(), ex.getMessage(), ex.getDetail());
	}
}