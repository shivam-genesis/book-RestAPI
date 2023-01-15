package com.bookrestAPI.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.bookrestAPI.dao.AuthorRepository;
import com.bookrestAPI.dao.BookRepository;
import com.bookrestAPI.entity.Author;
import com.bookrestAPI.entity.Book;
import com.bookrestAPI.exception.AlreadyExistException;
import com.bookrestAPI.exception.ResourceNotFoundException;
import com.bookrestAPI.exception.ValidationException;
import com.bookrestAPI.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private BookRepository bookRepository;

	@CachePut(value = "author", key = "#author")
	public Author addAuthor(Author author) {
		Optional<Author> existedAuthor = this.authorRepository.findById(author.getId());
		if (author.getId() == 0) {
			throw new ValidationException("Author","0");
		} else if (!existedAuthor.isEmpty()) {
			throw new AlreadyExistException("Author", String.valueOf(author.getId()));
		} else if (author.getFirstName().equals("") || author.getFirstName().length() < 3) {
			throw new ValidationException("FirstName ", "2");
		} else if (author.getLastName().equals("") || author.getLastName().length() < 3) {
			throw new ValidationException("LastName ", "2");
		}
		Author a = this.authorRepository.save(author);
		return a;
	}

	@Cacheable(value = "author", key = "#id")
	public Author getAuthorById(int id) {
		Author author = this.authorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Author ", " authorID: ", String.valueOf(id)));
		return author;
	}

	@Cacheable(value = "author", key = "#firstName")
	public Author getAuthorByFirstName(String firstName) {
		Author author = this.authorRepository.findByFirstName(firstName);
		if (author == null) {
			throw new ResourceNotFoundException("Author ", " authorID: ", firstName);
		}
		return author;
	}

	@Cacheable(value = "author")
	public List<Author> getAuthors() {
		List<Author> authors = this.authorRepository.findAll();
		if (authors.isEmpty()) {
			throw new ResourceNotFoundException("Author");
		}
		return authors;
	}

	@CachePut(value = "author", key = "#author.id")
	public Author updateAuthor(int id, Author author) {
		Author a = this.authorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Author ", " AuthorID: ", String.valueOf(id)));
		if (author.getFirstName().equals("") || author.getFirstName().length() < 3) {
			throw new ResourceNotFoundException("FirstName ", "", author.getFirstName());
		} else if (author.getLastName().equals("") || author.getLastName().length() < 3) {
			throw new ResourceNotFoundException("LastName ", "", author.getLastName());
		}
		a.setFirstName(author.getFirstName());
		a.setLastName(author.getLastName());
		this.authorRepository.save(a);
		return a;
	}

	@CacheEvict(value = "author", key = "#id")
	public void deleteAuthor(int id) {
		Author author = this.authorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Author ", "AuthorID", String.valueOf(id)));
		List<Book> books = this.bookRepository.findByAuthor(author);
		if (!books.isEmpty()) {
			for (Book b : books) {
				this.bookRepository.deleteById(b.getId());
			}
		}
		this.authorRepository.deleteById(id);
	}
}
