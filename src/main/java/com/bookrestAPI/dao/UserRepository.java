package com.bookrestAPI.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookrestAPI.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	User findByUsername(String username);
	void deleteByUsername(String username);
}
