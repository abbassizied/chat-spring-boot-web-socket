package com.sip.ams.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sip.ams.entities.User;
import com.sip.ams.services.UserService;

@Controller
public class ProfileController {

	private final UserService userService;

	public ProfileController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/profile")
	public String currentUserProfile(Model model, Principal principal) {

		String email = principal.getName();
		User currentUser = userService.findByEmail(email);

		model.addAttribute("currentUser", currentUser);
		return "pages/profile";
	}

	// GET method to display the change password form
	@GetMapping("/update-password")
	public String showChangePasswordForm() {
		return "pages/updatePassword"; // Return the Thymeleaf view name for the change password page
	}

}
