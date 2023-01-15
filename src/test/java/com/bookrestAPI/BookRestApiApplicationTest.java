package com.bookrestAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.bookrestAPI.dao.BookRepository;
import com.bookrestAPI.entity.Author;
import com.bookrestAPI.entity.Book;
import com.bookrestAPI.service.BookService;

@RunWith(SpringRunner.class)
@SpringBootTest
class BookRestApiApplicationTest {

	@Autowired
	private BookService bookService;

	@MockBean
	private BookRepository bookRepository;

	@Test
	public void addBookTest() {
		Book book = new Book(1, "Java", new Author());
		when(bookRepository.save(book)).thenReturn(book);
		assertEquals(book, bookService.addBook(book));
	}

	@Test
	public void getAllBooksTest() {
		when(bookRepository.findAll())
				.thenReturn(Stream.of(new Book(1, "Java", new Author()), new Book(2, "Python", new Author()))
						.collect(Collectors.toList()));
		assertEquals(2, bookService.getAllBooks().size());
	}
	
	@Test
	public void deleteBookByIdTest() {
		Book book = new Book(1,"Java",new Author());
		int id =1;
		bookService.deleteById(id);
		verify(bookRepository,times(1)).delete(book);
	}
}















