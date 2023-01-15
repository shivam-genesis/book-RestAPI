package com.bookrestAPI.service;

import java.util.List;

import com.bookrestAPI.entity.Author;

public interface AuthorService {
	
	Author addAuthor(Author author);
	
	Author getAuthorById(int id);
	
	Author getAuthorByFirstName(String firstName);
	
	List<Author> getAuthors();
	
	Author updateAuthor(int id, Author author);
	
	void deleteAuthor(int id);
	
}
