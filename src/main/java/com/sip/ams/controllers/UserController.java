package com.sip.ams.controllers;

import com.sip.ams.entities.OnCreate;
import com.sip.ams.entities.OnUpdate;
import com.sip.ams.entities.Role;
import com.sip.ams.entities.User;
import com.sip.ams.services.RoleService;
import com.sip.ams.services.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final RoleService roleService;

	public UserController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping("/")
	public String index() {
		return "redirect:list";
	}

	@GetMapping("list")
	public String list(Model model) {
		List<User> users = userService.getAllUsers();

		if (users.size() == 0)
			users = null;

		model.addAttribute("users", users);
		return "user/listUsers";
	}

	@GetMapping("add")
	public String showAddUserForm(Model model) {
		List<Role> allRoles = new ArrayList<Role>(roleService.getAllRoles());

		int nbreRoles = allRoles.size();
		model.addAttribute("nbreRoles", nbreRoles);
		model.addAttribute("roles", allRoles);
		model.addAttribute("user", new User());
		return "user/addUser";
	}

	@PostMapping("add")
	public String addUser(@Validated(OnCreate.class) User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "user/addUser";
		}
		user = userService.createUser(user);
		return "redirect:list";
	}

	@GetMapping("edit/{id}")
	public String showUserFormToUpdate(@PathVariable Integer id, Model model) {

		List<Role> allRoles = new ArrayList<Role>(roleService.getAllRoles());
		model.addAttribute("roles", allRoles);

		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "user/updateUser";
	}

	@PostMapping("update")
	public String updateUser(@Validated(OnUpdate.class) User user, BindingResult bindingResult, Model model) {

		// If there are validation errors, return to the edit form
		if (bindingResult.hasErrors()) {
			// Print validation errors to the console
			bindingResult.getAllErrors().forEach(error -> {
				System.out.println("Error: " + error.getDefaultMessage());
			});

			List<Role> allRoles = new ArrayList<Role>(roleService.getAllRoles());
			model.addAttribute("roles", allRoles);
			model.addAttribute("user", user);
			return "user/updateUser";
		}

		userService.updateUser(user);

		return "redirect:list";
	}

	@GetMapping("show/{id}")
	public String showUser(@PathVariable Integer id, Model model) {
		User user = this.userService.getUserById(id);
		model.addAttribute("user", user);
		return "user/showUser";
	}

	@GetMapping("delete/{id}")
	public String deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);
		return "redirect:../list";
	}

	@PostMapping("/update-password")
	public String updatePassword(@RequestParam("password") String password, Authentication authentication,
			Principal principal) {

		if (authentication == null || !authentication.isAuthenticated()) {
			return "redirect:/login"; // Redirect to login if not authenticated
		}

		String email = principal.getName();
		// Update the password using the provided email
		userService.updatePassword(email, password);
		// Redirect or return a success view
		return "redirect:/profile"; // or wherever you want to redirect after updating
	}

}
