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

import com.bookrestAPI.config.UsersExcelExporter;
import com.bookrestAPI.entity.User;
import com.bookrestAPI.exception.ApiResponse;
import com.bookrestAPI.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/user")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		User u = this.userService.addUser(user);
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}

	@GetMapping("/user/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
		User u = this.userService.getUserByUsername(username);
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = this.userService.getUsers();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@GetMapping("/users/export")
	public void downloadToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users.xlxs";
		response.setHeader(headerKey, headerValue);

		List<User> listUsers = userService.getUsers();
		UsersExcelExporter excelExporter = new UsersExcelExporter(listUsers);
		excelExporter.export(response);
	}

	@PutMapping("/user/{username}/{password}")
	public ResponseEntity<User> updateUser(@PathVariable("username") String username,
			@PathVariable("password") String password) {
		User u = this.userService.updateUser(username, password);
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}

	@DeleteMapping("/user/{username}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("username") String username) {
		this.userService.deleteUser(username);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted!!", true), HttpStatus.OK);
	}
}
