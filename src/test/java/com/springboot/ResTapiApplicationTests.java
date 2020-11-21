package com.springboot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.Controller.BookController;

@SpringBootTest
class ResTapiApplicationTests {
	
	@Autowired
	BookController bookController;
	

	@Test
	void contextLoads() {
		assertThat(bookController).isNotNull();
	}

}
