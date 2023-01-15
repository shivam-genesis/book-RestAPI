package com.bookrestAPI.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookrestAPI.config.BooksExcelDownload;
import com.bookrestAPI.config.BooksExcelUploader;
import com.bookrestAPI.entity.Book;
import com.bookrestAPI.exception.ApiResponse;
import com.bookrestAPI.service.BookService;

@RestController
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping("/book")
	public ResponseEntity<Book> addSingleBook(@RequestBody Book book) {
		Book b = this.bookService.addBook(book);
		return new ResponseEntity<Book>(b, HttpStatus.CREATED);
	}
	
	@PostMapping("/books/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        if (BooksExcelUploader.checkExcelFormat(file)) {
            this.bookService.saveDataFromExcelFile(file);
            return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to db"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");
    }

	@GetMapping("/books")
	public ResponseEntity<List<Book>> getBooks() {
		List<Book> books = this.bookService.getAllBooks();
		return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
	}

	@GetMapping("book/value/{value}")
	public ResponseEntity<List<Book>> getBookByValue(@PathVariable String value) {
		List<Book> book = this.bookService.getBookByValue(value);
		return new ResponseEntity<List<Book>>(book, HttpStatus.OK);
	}

	@GetMapping("/books/author-id/{author_id}")
	public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable int author_id) {
		List<Book> books = this.bookService.getBooksByAuthor(author_id);
		return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
	}

	@GetMapping("/books/export")
	public void downloadToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=books.xlxs";
		response.setHeader(headerKey, headerValue);

		List<Book> listBooks = bookService.getAllBooks();
		BooksExcelDownload excelExporter = new BooksExcelDownload(listBooks);
		excelExporter.export(response);
	}

	@PutMapping("/book/{id}")
	public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable("id") int id) {
		Book updateBook = this.bookService.updateBook(book, id);
		return new ResponseEntity<Book>(updateBook, HttpStatus.OK);
	}

	@DeleteMapping("/book/{id}")
	public ResponseEntity<ApiResponse> deleteBook(@PathVariable("id") int id) {
		this.bookService.deleteById(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Book Deleted!!", true), HttpStatus.OK);
	}
}