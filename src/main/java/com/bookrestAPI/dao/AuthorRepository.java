package com.bookrestAPI.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookrestAPI.entity.Author;

public interface AuthorRepository extends JpaRepository<Author,Integer>{

	public Author findByFirstName(String firstName);
}
