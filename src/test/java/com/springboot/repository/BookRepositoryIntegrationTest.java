package com.springboot.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.springboot.model.Book;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class BookRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	BookRepository bookRepository;

	@Test
	void findAlltest() {
		List<Book> bookList = bookRepository.findAll();
		assertNotNull(bookList);
		assertThat(bookList, hasSize(6));
		assertTrue(bookList.stream().anyMatch(book -> book.getBookAuthor().equals("Ash")));
	}

	@Test
	void findByIdtest() {
		Optional<Book> bookOptional = bookRepository.findById(1l);
		Book book = bookOptional.get();
		assertNotNull(book);
		assertThat("Ash", is(equalTo(book.getBookAuthor())));
		assertEquals(1, book.getBookId());
		assertEquals(1000, book.getBookPrice());

	}

	@Test
	void saveTest() {
		Book createBook = new Book();
		createBook.setBookName("Git Flow");
		createBook.setBookAuthor("He-man");
		createBook.setBookPrice(5000);
		entityManager.persist(createBook);

		Book saveBook = bookRepository.save(createBook);

		assertThat(createBook.getBookAuthor(), is(equalTo(saveBook.getBookAuthor())));
		assertEquals(createBook.getBookId(), saveBook.getBookId());
		assertEquals(createBook.getBookPrice(), saveBook.getBookPrice());
	}

	@Test
	void deleteTest() {

		Optional<Book> bookOptional = bookRepository.findById(28l);
		Book book = bookOptional.get();

		bookRepository.delete(book);

		Optional<Book> bookOptionaldeleted = bookRepository.findById(28l);
		assertFalse(bookOptionaldeleted.isPresent());

	}

}
