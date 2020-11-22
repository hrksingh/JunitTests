package com.springboot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.Controller.BookController;
import com.springboot.repository.BookRepository;
import com.springboot.service.BookCRUDService;

@SpringBootTest
class ResTapiApplicationTests {

	@Autowired
	BookController bookController;

	@Autowired
	BookCRUDService bookCRUDService;

	@Autowired
	BookRepository bookRepository;

	@Test
	void contextLoads() {
		
		assertThat(bookController).isNotNull();
		assertThat(bookCRUDService).isNotNull();
		assertThat(bookRepository).isNotNull();
	}

}
