package com.sip.ams.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime; 

import com.sip.ams.utils.validation.UniqueRoleName;

@Entity
@Table(name = "roles")
public class Role {

	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@UniqueRoleName // Custom validator to check for uniqueness
	@Pattern(regexp = "^ROLE_[A-Z]+$", message = "Role name must start with 'ROLE_' followed by uppercase letters only")
	@NotBlank(message = "Role name cannot be blank")
	@Column(nullable = false, unique = true)
	private String name;

	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdDate;

	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	@PrePersist
	protected void onCreate() {
		this.createdDate = LocalDateTime.now();
		this.updatedDate = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedDate = LocalDateTime.now();
	}

	// Constructors, getters, and setters
	public Role() {
		super();
	}

	public Role(String name) {
		super();
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate
				+ "]";
	}
	
}
