package com.springboot.Exception;

public class BookNotFoundException extends Exception{
	private Long id;
	public BookNotFoundException(Long id) {
		// TODO Auto-generated constructor stub
		super(String.format("Book is not found with id : '%s'", id));
	}

}
