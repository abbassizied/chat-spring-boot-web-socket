package com.sip.ams.configs;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
public class SecurityConfig {

	// 1 - Password encoder (BCrypt)
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 2 - JdbcUserDetailsManager for retrieving users and authorities from the
	// database
	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {

		String usersByUsernameQuery = "SELECT email AS username, password, active FROM users WHERE email = ?";

        String authsByUserQuery = "SELECT u.email AS username, r.name AS authority " +
                "FROM users u " +
                "JOIN user_role ur ON u.id = ur.user_id " +
                "JOIN roles r ON ur.role_id = r.id " +
                "WHERE u.email = ?";
		
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.setUsersByUsernameQuery(usersByUsernameQuery);
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(authsByUserQuery);

		return jdbcUserDetailsManager;
	}

	// 3 - DaoAuthenticationProvider using JdbcUserDetailsManager
	@Bean
	public AuthenticationProvider authenticationProvider(DataSource dataSource) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(jdbcUserDetailsManager(dataSource));
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	// 4 - AuthenticationManager that uses the custom AuthenticationProvider
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, DataSource dataSource) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.authenticationProvider(authenticationProvider(dataSource)).build();
	}

}