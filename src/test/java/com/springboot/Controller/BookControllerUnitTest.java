package com.springboot.Controller;

import static com.springboot.utils.JsonUtils.asJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.springboot.model.Book;
import com.springboot.service.BookCRUDService;;

@WebMvcTest
class BookControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BookCRUDService bookCRUDService;
	
	@InjectMocks
	BookController bookController;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void getAllBooksTest() throws Exception {
		Book mockBook1 = new Book(2L, "Java Tests part 2", "Ash", 2000);
		Book mockBook2 = new Book(3L, "mockito test", "Hrk", 1500);
		Book mockBook3 = new Book(4L, "Spring Boot demos", "Payal", 1000);

		List<Book> mockList = new ArrayList<>();
		mockList.add(mockBook1);
		mockList.add(mockBook2);
		mockList.add(mockBook3);
 
		when(bookCRUDService.findAllBooks()).thenReturn(mockList);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/books")
				    .accept(MediaType.APPLICATION_JSON))
		            .andDo(print())
				    .andExpect(status().isOk())
				    .andExpect(jsonPath("$.[0].bookId", is(mockBook1.getBookId().intValue())))
				    .andReturn();
	}

	@Test
	void getOneBookTest() throws Exception {
		Book book= new Book(1L, "Java Tests part 1", "Ash", 3000);
		
		when(bookCRUDService.findBookById(book.getBookId())).thenReturn(book);
		
	    mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}",1)
			   .accept(MediaType.APPLICATION_JSON))
	           .andDo(print())
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.bookId", is(book.getBookId().intValue())))
			   .andReturn();
	}
	
	@Test
	void createBookTest() throws Exception {
		Book newBook = new Book();
		newBook.setBookId(1l);
		newBook.setBookAuthor("Wolfie");
		newBook.setBookName("Wolf pack");
		newBook.setBookPrice(12000);

		when(bookCRUDService.saveBook(newBook)).thenReturn(newBook);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/createBook")
			   .accept(MediaType.APPLICATION_JSON)
			   .content(asJsonString(newBook))
			   .characterEncoding("utf-8")
			   .contentType(MediaType.APPLICATION_JSON))
		       .andDo(print())
			   .andExpect(status().isOk())
			   .andExpect(content().contentType("application/json"))
			   .andExpect(jsonPath("$.bookName", is(newBook.getBookName())))
			   .andReturn();

	}
	
	@Test
	void updateBookTest() throws Exception {
		Book newBook = new Book();
		newBook.setBookId(5l);
		newBook.setBookAuthor("Wolf");
		newBook.setBookName("Wolf of wall street");
		newBook.setBookPrice(19868);

		when(bookCRUDService.updateBook(newBook.getBookId(), newBook)).thenReturn(newBook);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/books/{id}", 5)
			   .accept(MediaType.APPLICATION_JSON)
			   .content(asJsonString(newBook))
			   .characterEncoding("utf-8")
			   .contentType(MediaType.APPLICATION_JSON))
		       .andDo(print())
			   .andExpect(status().isOk())
			   .andExpect(content().contentType("application/json"))
			   .andExpect(jsonPath("$.bookName", is(newBook.getBookName())))
			   .andReturn();
	}

	@Test
	void deleteBookTest() throws Exception {
		
		when(bookCRUDService.deleteBookById(5l)).thenReturn(new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/books/{id}",5).characterEncoding("utf-8"))
		       .andDo(print())
			   .andExpect(status().isNoContent())
			   .andReturn();
	}

}
