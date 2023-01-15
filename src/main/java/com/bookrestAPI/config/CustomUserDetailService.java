package com.bookrestAPI.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookrestAPI.dao.UserRepository;
import com.bookrestAPI.entity.User;

// For database Authentication
@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found!!"));
		return new CustomUserDetail(user);
	}
}
