package com.bookrestAPI.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookrestAPI.dao.UserRepository;
import com.bookrestAPI.entity.User;
import com.bookrestAPI.exception.ResourceNotFoundException;
import com.bookrestAPI.exception.ValidationException;
import com.bookrestAPI.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User addUser(User user) {
		if (user.getUsername() == null || user.getUsername().length() < 4) {
			throw new ValidationException("Username", "3");
		} else if (user.getPassword() == null || user.getPassword().length() < 4) {
			throw new ValidationException("Password", "3");
		} else if (user.getEmail() == null || user.getEmail().length() < 11) {
			throw new ValidationException("Email", "10");
		} else if (user.getRole() == null || user.getRole().length() < 2) {
			throw new ValidationException("Role", "1");
		}
		String encodedPassword = this.bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		String role = "ROLE_"+user.getRole().toUpperCase();
		user.setRole(role);
		User u = this.userRepository.save(user);
		return u;
	}

	@Override
	public User getUserByUsername(String username) {
		User user = this.userRepository.findByUsername(username);
		if (user == null) {
			throw new ResourceNotFoundException("User","username",username);
		}
		return user;
	}

	@Override
	public List<User> getUsers() {
		List<User> users = this.userRepository.findAll();
		if (users.isEmpty()) {
			throw new ResourceNotFoundException("User");
		}
		return users;
	}

	@Override
	public User updateUser(String username, String password) {
		User user = this.userRepository.findByUsername(username);
		if (user == null) {
			throw new ResourceNotFoundException("User ", "username", username);
		}
		String encodedPassword = this.bCryptPasswordEncoder.encode(password);
		user.setPassword(encodedPassword);
		User u = this.userRepository.save(user);
		return u;
	}

	@Override
	public void deleteUser(String username) {
		User user = this.userRepository.findByUsername(username);
		if (user == null) {
			throw new ResourceNotFoundException("User");
		}
		this.userRepository.deleteByUsername(username);
	}

}
