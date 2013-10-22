package com.github.woozoo73.srs.exception;

public class AlreadyExistException extends IllegalStateException {

	private static final long serialVersionUID = 1L;

	public AlreadyExistException() {
	}

	public AlreadyExistException(String message) {
		super(message);
	}

}
