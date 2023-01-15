package com.bookrestAPI.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bookrestAPI.entity.Book;

public interface BookService {

	Book addBook(Book book);
	
	void saveDataFromExcelFile(MultipartFile file);

	List<Book> getAllBooks();

	List<Book> getBookByValue(String value);

	List<Book> getBooksByAuthor(int author_id);

	Book updateBook(Book book, int id);

	void deleteById(int id);
}
