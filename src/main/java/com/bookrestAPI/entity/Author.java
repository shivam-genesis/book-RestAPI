package com.bookrestAPI.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author /* implements Serializable */ {

	@Id
	@NotNull
	@Column(name = "author_id")
	private int id;

	@Column(name = "author_first_name")
	private String firstName;

	@Column(name = "author_last_name")
	private String lastName;
	
	@Column(name= "author_email")
	private String email;

	// If the mapping is from both side of table it is called bidirectional mapping
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // This mappedBy property will
																						// not create the column of
																						// foreign key in Author table
	@JsonBackReference // Used to prevent from infinite recursion
	private List<Book> books = new ArrayList<>();
}