package com.rogera.bookclub.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.rogera.bookclub.models.LoginUser;
import com.rogera.bookclub.models.User;
import com.rogera.bookclub.services.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userServ;
	
	
	@GetMapping("/")
	public String index(
		@ModelAttribute("newUser") User emptyUser,
		@ModelAttribute("newLogin") LoginUser emptyLoginUser
	) {
		return "/user/index.jsp";
	}
	
	
	// --------------------- REGISTER LOGIC STARTS ------------------------------- //
	// PROCESS REGISTER
	@PostMapping("/register")
	public String register(
		@Valid @ModelAttribute("newUser") User filledUser,
		BindingResult results,
		HttpSession session,
		Model model
	) {
		User createdUser = userServ.register(filledUser, results);
		if(results.hasErrors()) {
			model.addAttribute("newLogin", new LoginUser());
			return "/user/index.jsp";
		}
		// SAVE THE USERS ID IN SESSION
		session.setAttribute("user_id", createdUser.getId());
		return "redirect:/books";
	}
	
	
	// --------------------- LOG IN LOGIC STARTS ------------------------------- //
	
	// PROCESS LOGIN
	@PostMapping("/login")
	public String login(
		@Valid @ModelAttribute("newLogin") LoginUser filledLoginUser,
		BindingResult results,
		HttpSession session,
		Model model
	) {
		User loggedUser = userServ.login(filledLoginUser, results);
		if(results.hasErrors()) {
			model.addAttribute("newUser", new User());
			return "/user/index.jsp";
		}
		session.setAttribute("user_id", loggedUser.getId());
		return "redirect:/books";
	}
	
	// CHECK THAT YOU ARE LOGGED IN
	@GetMapping("/homepage")
	public String homepage(
		HttpSession session
	) {
		if(session.getAttribute("user_id") == null) {
			return "redirect:/";
		}
		return "/user/dashboard.jsp";
		
	}
	// --------------------- LOG IN LOGIC ENDS ------------------------------- //	
	
	
	
	// --------------------- LOG OUT STARTS ------------------------------- //
	@GetMapping("/logout")
	public String logout(
			HttpSession session
	) {
		session.invalidate();
//		session.removeAttribute("user_id");
		return "redirect:/";
	}
	
	
	
//END BRACKET
}