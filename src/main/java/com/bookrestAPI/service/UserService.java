package com.bookrestAPI.service;

import java.util.List;

import com.bookrestAPI.entity.User;

public interface UserService {
	
	User addUser(User user);
	
	User getUserByUsername(String username);
	
	List<User> getUsers();
	
	User updateUser(String username,String password);
	
	void deleteUser(String username);
		
}
