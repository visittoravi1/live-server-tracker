package org.opengraph.lst.web.controllers;

import org.opengraph.lst.core.beans.ExceptionDTO;
import org.opengraph.lst.core.exceptions.NotImplmentedException;
import org.opengraph.lst.core.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomControllerAdvice {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomControllerAdvice.class);
	
	@ExceptionHandler(NotImplmentedException.class)
	@ResponseStatus(HttpStatus.TOO_EARLY)
	public ExceptionDTO handleNotImplmentedException(NotImplmentedException ex) {
		LOGGER.error("Configuration required!", ex);
		return new ExceptionDTO("Server is not yet configured!");
	}
	
	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ExceptionDTO handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
		LOGGER.error("Configuration required!", ex);
		return new ExceptionDTO(ex.getMessage());
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionDTO handleResourceNotFoundException(ResourceNotFoundException ex) {
		LOGGER.error("Not Found!", ex);
		return new ExceptionDTO(ex.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionDTO handleException(Exception ex) {
		LOGGER.error("Unknown exception", ex);
		return new ExceptionDTO(ex.getMessage());
	}

}
