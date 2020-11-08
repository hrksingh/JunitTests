package com.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.Exception.BookNotFoundException;
import com.springboot.model.Book;
import com.springboot.repository.BookRepository;

@Service
public class BookCRUDService {

	@Autowired
	BookRepository bookrepo;

	public List<Book> findAllBooks() {
		return bookrepo.findAll();

	}

	public Book findBookById(Long id) throws BookNotFoundException {
		return bookrepo.findById(id).orElseThrow(() -> new BookNotFoundException(id));
	}

	public Book saveBook(Book book) {
		return bookrepo.save(book);
	}

	public Book updateBook(Long id, Book bookDetails) throws BookNotFoundException {
		Book book = bookrepo.findById(id).orElseThrow(() -> new BookNotFoundException(id));

		book.setBookName(bookDetails.getBookName());
		book.setBookAuthor(bookDetails.getBookAuthor());
		book.setBookPrice(bookDetails.getBookPrice());

		Book updatedBook = bookrepo.save(book);
		return updatedBook;

	}

	public ResponseEntity<Void> deleteBookById(Long id) throws BookNotFoundException {
		Book book = bookrepo.findById(id).orElseThrow(() -> new BookNotFoundException(id));
		bookrepo.delete(book);
		return ResponseEntity.noContent().build();
	}

}
