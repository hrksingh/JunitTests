package com.springboot.Exception;

public class BookNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public BookNotFoundException(Long id) {
		super(String.format("Book is not found with id : '%s'", id));
	}

}
