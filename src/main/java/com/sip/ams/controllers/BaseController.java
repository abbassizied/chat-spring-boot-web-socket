package com.sip.ams.controllers;

import java.security.Principal; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
 
import com.sip.ams.entities.User;
import com.sip.ams.services.UserService;

@Controller
public class BaseController {

	private final UserService userService;

	public BaseController(UserService userService) {
		this.userService = userService;
	}	
	
	
	@GetMapping("/")
	public String index() {
		return "redirect:home";
	}	
	
	@GetMapping("home")
	public String homePage(Model model, Principal principal) {

		String email = principal.getName();
		User user = userService.findByEmail(email);

		System.out.println("User: " + user.toString());

		model.addAttribute("firstName", user.getFirstName());
		model.addAttribute("lastName", user.getLastName());
		model.addAttribute("authorities", user.getAuthorities());

		model.addAttribute("message", "Welcome to the home page!");

		return "pages/home"; // Returns home.html
	}	
 
}
