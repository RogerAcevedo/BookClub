package com.rogera.bookclub.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rogera.bookclub.models.Book;
import com.rogera.bookclub.repositories.BookRepository;

@Service
public class BookService {

	@Autowired
	BookRepository bookRepo;
	
// CREATE & UPDATE/EDIT
	public Book create(Book newBook) {
		return bookRepo.save(newBook);
	}
	
// READ ONE
	public Book getOne(Long id) {
		return bookRepo.findById(id).orElse(null);
	}
	

// READ ALL
	public List<Book> getAl(){
		return bookRepo.findAll();
	}
	

// DELETE
	public void deleteOne(Long id) {
		bookRepo.deleteById(id);
	}
	
	
// CLOSING BRACKET
}
