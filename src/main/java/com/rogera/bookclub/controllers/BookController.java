package com.rogera.bookclub.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.rogera.bookclub.models.Book;
import com.rogera.bookclub.services.BookService;

@Controller
public class BookController {
	@Autowired
	BookService bookServ;
	
	// ------------------------- CREATE ----------------------- //
	@GetMapping("/books/new")
	public String newBook(
		@ModelAttribute("bookObj") Book emptyBook,
		HttpSession session
	) {
		if(session.getAttribute("user_id") == null) {
			return "redirect:/";
		}
		return "/books/newbook.jsp";
	}
	
	@PostMapping("/books/new")
	public String processbook(
		@Valid @ModelAttribute("bookObj") Book filledBook,
		BindingResult results
	) {
		// VALIDATIONS FAIL
		if(results.hasErrors()) {
			return "/books/newbook.jsp";
		}
// WE HAVE ACCESS TO THE BOOK ID BECAUSE IT RETURNS A BOOK. create method returns a new book, with ID included
		Book newBook = bookServ.create(filledBook);
//		return "redirect:/books/" + newBook.getId();
		return "redirect:/books";
	}
	// ------------------------- CREATE END ----------------------- //	
	
	
	
	
	// ------------------------- READ ALL ----------------------- //
	@GetMapping("/books")
	public String index(
		HttpSession session, Model model
	) {
// LOGIC TO HAVE USER BE LOGGED IN BEFORE THEY CAN DO IN OUR APP
		if(session.getAttribute("user_id") == null) {
			return "redirect:/";
		}
		model.addAttribute("allBooks", bookServ.getAl());
		return "/books/index.jsp";
	}
	// ------------------------- READ ALL END ----------------------- //
	
	
	
	
	// ------------------------- READ ONE ----------------------- //
	
	@GetMapping("/books/{id}")
	public String oneBook(
		@PathVariable("id") Long id,
		Model model
	) {
		model.addAttribute("oneBook", bookServ.getOne(id) );
		return "/books/showone.jsp";
	}
	
	// ------------------------- READ ONE END ----------------------- //
	
	
	
	
	// ------------------------- UPDATE ----------------------- //
	@GetMapping("/books/{id}/edit")
	public String edit(
		@PathVariable("id") Long id,
		Model model
	) {
		model.addAttribute("bookObj", bookServ.getOne(id) );
		return "/books/editbook.jsp";
	}
	
	@PutMapping("/books/{id}/edit")
	public String update(
		@Valid @ModelAttribute("bookObj") Book filledBook,
		BindingResult results
	) {
		if(results.hasErrors()) {
			return "/books/editbook.jsp";
		}
		bookServ.create(filledBook);
		return "redirect:/books/{id}";
	}
	
	// ------------------------- UPDATE END ----------------------- //
	
	
	
	// ------------------------- DELETE ----------------------- //
	@GetMapping("/books/{id}/delete")
	public String delete(
		@PathVariable("id") Long id
	) {
		bookServ.deleteOne(id);
		return "redirect:/books";
	}
	
// OTHER WAY TO DELETE WITH DeleteMapping
//	@DeleteMapping("/books/{id}")
//	public String deleteBook(
//		@PathVariable("id") Long id	
//	) {
//		bookServ.deleteOne(id);
//		return "redirect:/books";
//	}
	
	// ------------------------- DELETE END ----------------------- //
	
	

	
	
// CLOSING BRACKET	
}
