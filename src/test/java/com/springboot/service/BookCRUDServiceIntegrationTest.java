package com.springboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.springboot.model.Book;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class BookCRUDServiceIntegrationTest {
	
	@Autowired
	BookCRUDService bookCRUDServices;

	@Test
	void saveBookTest() {
		//creating Book Instance
		Book book =  new Book(1L, "Java Tests", "Ash", 1000);
		
		//saveBook method Saved record in Database so this method is working fine
		Book savedBook = bookCRUDServices.saveBook(book);
		
		assertNotNull(savedBook);
		assertNotNull(savedBook.getBookId());
		assertEquals(1L, savedBook.getBookId());
		assertEquals("Ash", savedBook.getBookAuthor());
	}

}
