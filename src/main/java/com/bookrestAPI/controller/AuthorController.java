package com.bookrestAPI.controller;

import java.io.IOException;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.bookrestAPI.config.AuthorsExcelExporter;
import com.bookrestAPI.entity.Author;
import com.bookrestAPI.exception.ApiResponse;
import com.bookrestAPI.service.AuthorService;

@RestController
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	@PostMapping("/author")
	public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
		Author createdAuthor = this.authorService.addAuthor(author);
		return new ResponseEntity<Author>(createdAuthor, HttpStatus.CREATED);
	}

	@GetMapping("/author/{id}")
	public ResponseEntity<Author> getAuthorById(@PathVariable("id") int id) {
		Author retrieveAuthor = this.authorService.getAuthorById(id);
		return new ResponseEntity<Author>(retrieveAuthor, HttpStatus.OK);
	}
	
	@GetMapping("/author/firstName/{firstName}")
	public ResponseEntity<Author> getAuthorByFirstName(@PathVariable("firstName") String firstName){
		Author author = this.authorService.getAuthorByFirstName(firstName);
		return new ResponseEntity<Author>(author,HttpStatus.OK);
	}

	@GetMapping("/authors")
	public ResponseEntity<List<Author>> getAuthors() {
		List<Author> allAuthors = this.authorService.getAuthors();
		return new ResponseEntity<List<Author>>(allAuthors, HttpStatus.OK);
	}
	
	@GetMapping("/authors/export")
	public void downloadToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=authors.xlxs";
		response.setHeader(headerKey, headerValue);

		List<Author> listAuthors = authorService.getAuthors();
		AuthorsExcelExporter excelExporter = new AuthorsExcelExporter(listAuthors);
		excelExporter.export(response);
	}

	@PutMapping("/author/{firstname}")
	public ResponseEntity<Author> updateAuthor(@PathVariable("id") int id, @RequestBody Author author) {
		Author updatedAuthor = this.authorService.updateAuthor(id, author);
		return new ResponseEntity<Author>(updatedAuthor, HttpStatus.OK);
	}

	@DeleteMapping("/author/{id}")
	public ResponseEntity<ApiResponse> deleteAuthor(@PathVariable("id") int id) {
		this.authorService.deleteAuthor(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Author Deleted!!", true), HttpStatus.OK);
	}
}