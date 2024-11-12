package com.sip.ams.utils.validation;

import com.sip.ams.services.RoleService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueRoleNameValidator implements ConstraintValidator<UniqueRoleName, String> {

    private final RoleService roleService;

    public UniqueRoleNameValidator(RoleService roleService) {
        this.roleService = roleService;
    }
 
	@Override
	public boolean isValid(String roleName, ConstraintValidatorContext context) {

        // If the table is empty, allow the role name (no need to query for duplicates)
        if (roleService.getAllRoles() == null || roleService.getAllRoles().isEmpty()) {
            return true;
        }

       // If the table has records, check if the role name already exists (case insensitive)
		return !this.roleService.existsByNameIgnoreCase(roleName); 
	}
}