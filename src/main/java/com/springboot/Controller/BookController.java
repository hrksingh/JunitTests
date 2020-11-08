package com.springboot.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.Exception.BookNotFoundException;
import com.springboot.model.Book;
import com.springboot.repository.BookRepository;
import com.springboot.service.BookCRUDService;

@RestController
public class BookController {

	@Autowired
	BookCRUDService bookService;

	// Get All Books Details
	@GetMapping("/books")
	public List<Book> getAllBooks() {
		return bookService.findAllBooks();
	}

	// Get one book by bookId
	@GetMapping("/books/{id}")
	public Book getOneBook(@PathVariable Long id) throws BookNotFoundException{
		return bookService.findBookById(id);
	}

	// Create a new Book
	@PostMapping("/books")
	public Book createBook(@RequestBody Book book) {
		return bookService.saveBook(book); 
	}

	// Update Book Details
	@PutMapping("/books/{id}")
	public Book updateBook(@PathVariable Long id, @RequestBody Book bookDetails) throws BookNotFoundException {
		return bookService.updateBook(id, bookDetails);
	}

	// Delete a book
	@DeleteMapping("/books/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) throws BookNotFoundException {
		return bookService.deleteBookById(id);
	}
}
