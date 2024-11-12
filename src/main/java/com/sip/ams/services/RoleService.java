package com.sip.ams.services;

import com.sip.ams.entities.Role;
import com.sip.ams.repositories.RoleRepository;
import com.sip.ams.repositories.UserRepository;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService {

	private final RoleRepository roleRepository;
	private final UserRepository userRepository;

	public RoleService(final RoleRepository roleRepository, final UserRepository userRepository) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}

	public boolean existsByNameIgnoreCase(String roleName) {
		return roleRepository.existsByNameIgnoreCase(roleName);
	}

	public Role getRoleById(Integer roleId) {

		return this.roleRepository.findById(roleId)
				.orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + roleId));
	}

	public Role saveRole(Role role) {
		return this.roleRepository.save(role);
	}

	public Set<Role> getAllRoles() {
	    Set<Role> roles = new HashSet<>(this.roleRepository.findAll());
	    roles.forEach(role -> {
	        // Log role details to check for anomalies
	        System.out.println("Role: " + role);
	    });
	    return roles;
	}

	public void deleteRole(Integer id) {
		Role role = getRoleById(id);
		this.roleRepository.delete(role);
		// remove many-to-many relations at owning side
		userRepository.findAllByRoles(role).forEach(user -> user.getRoles().remove(role));
		this.roleRepository.delete(role);

	}

	public Role findRoleByName(String name) {
		return this.roleRepository.findByName(name)
				.orElseThrow(() -> new IllegalArgumentException("Role not found with name: " + name));
	}
}
