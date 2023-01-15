package com.bookrestAPI.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookrestAPI.entity.Author;
import com.bookrestAPI.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	List<Book> findByAuthor(Author author);
	
	
	//@Modifying //Used for modifying queries like delete, update, create 
	//Find by title and ID
	@Query(value="select * from books b where b.book_id like :keyword% or b.book_title like :keyword%",nativeQuery=true)
	List<Book> findByKeyword(@Param("keyword") String keyword);
}