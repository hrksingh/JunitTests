package com.springboot.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.springboot.model.Book;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup("classpath:dataset.xml")
public class BookRepoDBunitTest {

	@Autowired
	BookRepository bookRepository;

	@Test
	void findByIdtest() {
		Optional<Book> optionalBook = bookRepository.findById(1L);
		Book book = optionalBook.get();
		assertThat(book.getBookAuthor(), is(equalTo("Thanos")));
	}

	@Test
	void findAlltest() {
		List<Book> bookList = bookRepository.findAll();
		assertThat(bookList).isNotNull().isNotEmpty().hasSize(4);
		assertTrue(bookList.stream().anyMatch(book -> book.getBookAuthor().equals("Hrk")));
	}

	@Test
	void savetest() {
		Book book = new Book();
		book.setBookAuthor("Ash");
		book.setBookName("Ghost of Tushima");
		book.setBookPrice(3734);
		Book savedBook = bookRepository.save(book);
		assertThat(savedBook).isNotNull();
		assertThat(savedBook.getBookAuthor(), is(equalTo(book.getBookAuthor())));
	}

	@Test
	void deletetest() {
		Optional<Book> optionalBook = bookRepository.findById(1L);
		Book book = optionalBook.get();
		bookRepository.delete(book);
		Optional<Book> bookOptionaldeleted = bookRepository.findById(1l);
		assertFalse(bookOptionaldeleted.isPresent());
	}
}
