package com.github.woozoo73.srs.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.woozoo73.srs.domain.ServerError;
import com.github.woozoo73.srs.exception.NoContentException;
import com.github.woozoo73.srs.exception.NotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

	protected Log log = LogFactory.getLog(getClass());

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ResponseBody
	public ServerError handleException(NoContentException ex) {
		return new ServerError(ex);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ServerError handleException(IllegalStateException ex) {
		return new ServerError(ex);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ServerError handleException(NotFoundException ex) {
		return new ServerError(ex);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ServerError handleException(Exception ex) {
		return new ServerError(ex);
	}

}
