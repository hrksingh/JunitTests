package com.springboot.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.springboot.Exception.BookNotFoundException;
import com.springboot.model.Book;
import com.springboot.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class BookCRUDServiceUnitTest {

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookCRUDService bookCRUDService;

	@BeforeEach
	public void init() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testSaveBook() {
		// creating Book Instance
		Book mockBook = new Book(2L, "Java Tests part 2", "Ash", 2000);
		when(bookRepository.save(any(Book.class))).thenReturn(mockBook);

		Book savedBook = bookCRUDService.saveBook(new Book());

		// using hamcrest library as example only
		assertThat("Ash", is(equalTo(savedBook.getBookAuthor())));

	}

	@Test
	void findAllBooks() {
		Book mockBook1 = new Book(2L, "Java Tests part 2", "Ash", 2000);
		Book mockBook2 = new Book(3L, "mockito test", "Hrk", 1500);
		Book mockBook3 = new Book(4L, "Spring Boot demos", "Payal", 1000);

		List<Book> mockList = new ArrayList<>();
		mockList.add(mockBook1);
		mockList.add(mockBook2);
		mockList.add(mockBook3);

		when(bookRepository.findAll()).thenReturn(mockList);

		List<Book> booksList = bookCRUDService.findAllBooks();

		assertNotNull(booksList);
		assertThat(booksList, hasSize(3));
		assertThat(booksList, hasItems(mockBook1, mockBook2, mockBook3));
		assertThat(booksList, containsInAnyOrder(hasProperty("bookAuthor", is("Ash")),
				hasProperty("bookAuthor", is("Hrk")), hasProperty("bookAuthor", is("Payal"))));

	}

	@Test
	void testupdateBook() {
		Book mockBook3 = new Book(4L, "Spring Boot demos", "Payal", 1000);
		mockBook3.setBookName("Spring Boot Examples");
		when(bookRepository.save(any(Book.class))).thenReturn(mockBook3);

		Book savedBook = bookCRUDService.saveBook(new Book());

		assertThat("Spring Boot Examples", is(equalTo(savedBook.getBookName())));

	}

	@Test
	void testdeleteBookById() {

		try {
			bookCRUDService.deleteBookById((long) anyInt());
		} catch (BookNotFoundException e) {
			assertEquals("Book is not found with id : '0'", e.getMessage());
		}
	}

	@Test
	void testfindBookById() {
		Optional<Book> mockBook = Optional.ofNullable(new Book(2L, "Java Tests part 2", "Ash", 2000));

		when(bookRepository.findById(2L)).thenReturn(mockBook);

		when(bookRepository.findById(3L)).thenThrow(new BookNotFoundException(3L));

		Book foundBook = null;
		try {
			foundBook = bookCRUDService.findBookById(2L);
			bookCRUDService.findBookById(3L);
		} catch (BookNotFoundException e) {
			assertEquals("Book is not found with id : '3'", e.getMessage());
		}
		assertEquals(2, foundBook.getBookId());
		assertEquals(2000, foundBook.getBookPrice());
		assertEquals("Ash", foundBook.getBookAuthor());

	}

}
