package com.sip.ams.controllers;

import com.sip.ams.entities.Role; 
import com.sip.ams.services.RoleService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/roles/")
public class RoleController {

	private final RoleService roleService;

	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@GetMapping("/")
	public String index() {
		return "redirect:list";
	}	

	@GetMapping("list")
	public String listRoles(Model model) {
		List<Role> roles = new ArrayList<Role>(roleService.getAllRoles());

		if (roles.size() == 0)
			roles = null;

		model.addAttribute("roles", roles);
		return "role/listRoles";
	}

	@GetMapping("add")
	public String showAddRoleForm(Model model) {
		Role role = new Role("ROLE_USER");
		model.addAttribute("role", role);
		return "role/addRole";
	}

	@PostMapping("add")
	public String addRole(@ModelAttribute @Valid  final Role role, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "role/addRole";
		
		String roleName = role.getName();
		Role newRole = new Role(roleName);
		
		this.roleService.saveRole(newRole); 
		return "redirect:list";
	}
	
	
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable  final Integer id,  final Model model) {
	    Role role = roleService.getRoleById(id);
	    model.addAttribute("role", role);
	    return "role/updateRole";
	}

	@PostMapping("update")
	public String updateUser(@Valid Role role, BindingResult bindingResult, Model model) {
	    if (bindingResult.hasErrors()) {
	        return "role/updateRole"; // If validation fails, return the form view
	    }
	    
	    this.roleService.saveRole(role); // Save the updated role
		return "redirect:list";
	}	
	
	
	@GetMapping("/delete/{id}")
	public String deleteRole(@PathVariable Integer id, Model model) { 
	    roleService.deleteRole(id);
	    return "redirect:../list";
	}	
	

}
