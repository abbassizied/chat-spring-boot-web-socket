package com.sip.ams.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sip.ams.utils.validation.UniqueEmail;

@Entity
@Table(name = "users") // "user" is a reserved keyword in some databases, so we use "users".
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "First name cannot be blank", groups = { OnCreate.class, OnUpdate.class })
	@Column(nullable = false)
	@Pattern(regexp = "^[A-Za-z]+$", message = "The first name must contain only letters")
	private String firstName;

	@NotBlank(message = "Last name cannot be blank", groups = { OnCreate.class, OnUpdate.class })
	@Column(nullable = false)
	@Pattern(regexp = "^[A-Za-z]+$", message = "The last name must contain only letters")
	private String lastName;

	@UniqueEmail(groups = OnCreate.class)
	@NotBlank(message = "Email cannot be blank", groups = OnCreate.class) // Only validated on creation
	@Email(message = "Email should be valid", groups = OnCreate.class) // Only validated on creation
	@Pattern(regexp = ".+@.+\\..+", message = "Please enter a valid email address (e.g., example@domain.com)")
	@Column(nullable = false, unique = true)
	private String email;

	@NotBlank(message = "Password cannot be blank", groups = OnCreate.class) // Only validated on creation
	@Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters", groups = OnCreate.class) // Only
																													// validated
																													// on
																													// creation
	@Column(nullable = false)
	private String password;

	@NotNull(message = "Active status cannot be null", groups = { OnCreate.class, OnUpdate.class })
	private Boolean active = true; // Set default to true

	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdDate;

	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	// Many-to-many relationship between User and Role
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@PrePersist
	protected void onCreate() {
		// this.active = true;
		this.createdDate = LocalDateTime.now();
		this.updatedDate = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedDate = LocalDateTime.now();
	}

	// Constructors, getters, and setters
	public User() {
	}

	public User(String firstName, String lastName, String email, String password, Boolean active, Set<Role> roles) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.active = active;
		this.roles = roles;
	}

	// Getters and Setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", active=" + active + ", createdDate=" + createdDate + ", updatedDate="
				+ updatedDate + ", roles=" + roles + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return this.email;
	}

}
