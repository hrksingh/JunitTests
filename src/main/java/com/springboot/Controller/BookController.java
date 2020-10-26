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

@RestController
public class BookController {

	@Autowired
	BookRepository bookrepo;

	// Get All Books Details
	@GetMapping("/books")
	public List<Book> getAllBooks() {
		return bookrepo.findAll();
	}

	// Get one book by bookId
	@GetMapping("/books/{id}")
	public Book getOneBook(@PathVariable Long id) throws BookNotFoundException {
		return bookrepo.findById(id).orElseThrow(() -> new BookNotFoundException(id));
	}

	// Create a new Book
	@PostMapping("/books")
	public Book createBook(@RequestBody Book book) {
		return bookrepo.save(book);
	}

	// Update Book Details
	@PutMapping("/books/{id}")
	public Book updateBook(@PathVariable Long id, @RequestBody Book updatedbook) throws BookNotFoundException {

		Book book = bookrepo.findById(id).orElseThrow(() -> new BookNotFoundException(id));

		book.setBookName(updatedbook.getBookName());
		book.setBookAuthor(updatedbook.getBookAuthor());
		book.setBookPrice(updatedbook.getBookPrice());

		Book updatedBook = bookrepo.save(book);

		return updatedBook;

	}
	
	//Delete a book
	@DeleteMapping("/books/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable Long id) throws BookNotFoundException{
		Book book = bookrepo.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
		bookrepo.delete(book);
		return ResponseEntity.ok().build();
	}
}
