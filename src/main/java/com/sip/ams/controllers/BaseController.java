package com.sip.ams.controllers;

import java.security.Principal;
import java.util.List;
 
import org.springframework.security.core.Authentication;
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
	
	@GetMapping("/public-chat")
    public String publicChat(Model model, Authentication authentication) {
		List<User> users = userService.getAllUsers();

		if (users.size() == 0)
			users = null;

		model.addAttribute("users", users);
		model.addAttribute("username", authentication.getName());
		//****************************
		System.out.println("********************************************************");
		System.out.println(authentication.getName());
		System.out.println("********************************************************");
		//****************************
		
		
        return "pages/public-chat";
    }	
	

    /**
     * Handles the display of the private chat page.
     */
    @GetMapping("/private-chat/{id}")
    public String privateChatPage(@PathVariable Integer id, Principal principal, Model model) {
        User currentUser = userService.findByEmail(principal.getName()); // Get the current user
        User recipient = userService.getUserById(id); // Find the user you're chatting with
        
        if (recipient == null) {
            return "error"; // Handle the case where the recipient does not exist
        }

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("recipient", recipient);
        return "pages/private-chat"; // Return the private chat view
    }
 
}
