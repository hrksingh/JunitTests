package com.springboot.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.springboot.Exception.BookNotFoundException;
import com.springboot.model.Book;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class BookCRUDServiceIntegrationTest {

	@Autowired
	BookCRUDService bookCRUDServices;

	@Test
	void saveBookTest() {
		// creating Book Instance
		Book book = new Book(1L, "Java Tests", "Ash", 1000);

		// saveBook method Saved record in Database so this method is working fine
		Book savedBook = bookCRUDServices.saveBook(book);

		assertNotNull(savedBook);
		assertNotNull(savedBook.getBookId());
		assertEquals(1L, savedBook.getBookId());
		assertEquals("Ash", savedBook.getBookAuthor());
	}

	@Test
	void findAllBooksTest() {
		List<Book> booksList = bookCRUDServices.findAllBooks();
		assertNotNull(booksList);
		assertThat(booksList, hasSize(4));
	}

	@Test
	void findBookById() {
		Book book = bookCRUDServices.findBookById(1L);
		assertThat("Ash", is(equalTo(book.getBookAuthor())));
		assertEquals(1, book.getBookId());
		assertEquals(1000, book.getBookPrice());

		// testing exception
		BookNotFoundException thrown = assertThrows(BookNotFoundException.class,() -> bookCRUDServices.findBookById(8L));
		assertTrue(thrown.getMessage().contains("Book is not found with id : '8'"));
	}

	@Test
	void updateBook() {
		Book updatedBook = new Book();
		updatedBook.setBookName("Head First");
		updatedBook.setBookAuthor("Hrk");
		updatedBook.setBookPrice(5000);
		Book book = bookCRUDServices.updateBook(2L, updatedBook);

		assertThat("Hrk", is(equalTo(book.getBookAuthor())));
		assertEquals(2, book.getBookId());
		assertEquals(5000, book.getBookPrice());
	}
	
	@Test
	void deleteBookById() {
		ResponseEntity<Void> response = bookCRUDServices.deleteBookById(4l);
		assertTrue(response.getStatusCode().equals(HttpStatus.NO_CONTENT));
		assertThat(204, is(equalTo(response.getStatusCodeValue())));
		assertNull(response.getBody());
	}

}
