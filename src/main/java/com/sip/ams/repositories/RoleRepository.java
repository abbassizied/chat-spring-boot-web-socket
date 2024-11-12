package com.sip.ams.repositories; 

import com.sip.ams.entities.Role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	boolean existsByNameIgnoreCase(String name);
	Optional<Role> findByName(String name); 
}
