package com.springboot.Controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.springboot.model.Book;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class BookControllerIntegrationTest {

	@Autowired
	BookController bookController;

	@Test
	@Order(1)
	void getAllBooksTest() {
		List<Book> allBooks = bookController.getAllBooks();
		assertNotNull(allBooks);
		assertThat(allBooks, hasSize(5));
		assertThat(allBooks, containsInAnyOrder(hasProperty("bookAuthor", is("Ash")),
				hasProperty("bookAuthor", is("Hrk")), hasProperty("bookAuthor", is("Payal")),hasProperty("bookAuthor", is("Wolfie"))));
	}

	@Test
	@Order(2)
	void getOneBookTest() {
		Book book = bookController.getOneBook(1L);
		assertThat("Ash", is(equalTo(book.getBookAuthor())));
		assertEquals(1, book.getBookId());
		assertEquals(1000, book.getBookPrice());
	}

	@Test
	@Order(3)
	void createBookTest() {
		Book newBook = new Book();
		newBook.setBookAuthor("Wolfie");
		newBook.setBookName("Wolf pack");
		newBook.setBookPrice(12000);

		Book book = bookController.createBook(newBook);

		assertThat("Wolfie", is(equalTo(book.getBookAuthor())));
		assertEquals(12000, book.getBookPrice());
	}
	
	@Test
	@Order(4)
	void UpdateBookTest() {
		Book bookUpdated = new Book();
		bookUpdated.setBookAuthor("Wolfie");
		bookUpdated.setBookName("Wolf pack 2");
		bookUpdated.setBookPrice(12000);

		Book book = bookController.updateBook(16L, bookUpdated);

		assertThat("Wolfie", is(equalTo(book.getBookAuthor())));
		assertThat("Wolf pack 2", is(equalTo(book.getBookName())));
		assertEquals(16, book.getBookId());
		assertEquals(12000, book.getBookPrice());
	}
	
	@Test
	@Order(5)
	void deleteBookTest() {
		ResponseEntity<Void> response = bookController.deleteBook(17l);
		assertTrue(response.getStatusCode().equals(HttpStatus.NO_CONTENT));
		assertThat(204, is(equalTo(response.getStatusCodeValue())));
		assertNull(response.getBody());

	}
}
