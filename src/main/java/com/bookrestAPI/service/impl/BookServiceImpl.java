package com.bookrestAPI.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bookrestAPI.config.BooksExcelUploader;
import com.bookrestAPI.dao.AuthorRepository;
import com.bookrestAPI.dao.BookRepository;
import com.bookrestAPI.entity.Author;
import com.bookrestAPI.entity.Book;
import com.bookrestAPI.exception.AlreadyExistException;
import com.bookrestAPI.exception.ResourceNotFoundException;
import com.bookrestAPI.exception.ValidationException;
import com.bookrestAPI.service.AuthorService;
import com.bookrestAPI.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private AuthorService authorService;

	@Transactional
	@CachePut(value = "book", key = "#book.id")
	public Book addBook(Book book) {
		Optional<Book> existedBook = this.bookRepository.findById(book.getId());
		if (book.getId() == 0) {
			throw new ValidationException("Book", "0");
		} else if (!existedBook.isEmpty()) {
			throw new AlreadyExistException("Book", String.valueOf(book.getId()));
		} else if (book.getTitle().equals("") || book.getTitle().length() < 3) {
			throw new ValidationException("Title", "2");
		} else if (book.getAuthor() == null) {
			throw new ValidationException("Author");
		}
		int authorId = book.getAuthor().getId();
		Optional<Author> author = this.authorRepository.findById(authorId);
		if (author.isEmpty()) {
			this.authorService.addAuthor(book.getAuthor());
		}
		Book result = this.bookRepository.save(book);
		return result;
	}
	
	@Transactional
	public void saveDataFromExcelFile(MultipartFile file) {
		try {
			List<Book> books = BooksExcelUploader.convertExcelToListOfBooks(file.getInputStream());
			System.out.println(books);
			this.bookRepository.saveAll(books);
			for (Book b : books) {
				Optional<Author> author = this.authorRepository.findById(b.getAuthor().getId());
				if (author.isEmpty()) {
					this.authorService.addAuthor(b.getAuthor());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Cacheable(value = "book", key = "#value")
	public List<Book> getBookByValue(String value) {
		List<Book> books = this.bookRepository.findByKeyword(value);
		if (books.isEmpty()) {
			throw new ResourceNotFoundException("Book ", " value", value);
		}
		return books;
	}

	@Cacheable(value = "book", key = "#author_id")
	public List<Book> getBooksByAuthor(int author_id) {
		Author author = this.authorService.getAuthorById(author_id);
		List<Book> books = this.bookRepository.findByAuthor(author);
		if (books.isEmpty()) {
			throw new ResourceNotFoundException("Books ", " authorID ", String.valueOf(author_id));
		}
		return books;
	}

	@Cacheable(value = "book")
	public List<Book> getAllBooks() {
		List<Book> books = this.bookRepository.findAll();
		if (books.isEmpty()) {
			throw new ResourceNotFoundException("Books");
		}
		return books;
	}

	@CachePut(value = "book", key = "#book.id")
	public Book updateBook(Book book, int id) {
		Book retrievedBook = this.bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book ", " bookID ", String.valueOf(id)));
		if (book.getTitle().equals("") || book.getTitle().length() < 3) {
			throw new ValidationException("Title", "2");
		} else if (book.getAuthor() == null) {
			throw new ValidationException("Author");
		}
		retrievedBook.setId(id);
		retrievedBook.setTitle(book.getTitle());
		retrievedBook.setAuthor(book.getAuthor());
		Book updatedBook = this.bookRepository.save(retrievedBook);
		return updatedBook;
	}

	@CacheEvict(value = "book", key = "#id")
	public void deleteById(int id) {
		this.bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book ", " bookID ", String.valueOf(id)));
		this.bookRepository.deleteById(id);
	}
}