package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	@Autowired
	private DataSource dataSource;
	
	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((requests) -> requests
						.anyRequest().permitAll())
				.formLogin((form) -> form
						.loginPage("/login")
						.permitAll()
						.failureUrl("/login")
						//.failureHandler(authenticationFailureHandler())
						.usernameParameter("email")
						.passwordParameter("password")
						 .defaultSuccessUrl("/applicant/home"))
						//.successHandler(authenticationSuccessHandler()))
				.logout((logout) -> logout
						.logoutSuccessUrl("/")
						.invalidateHttpSession(true)
						.permitAll());

		return http.build();
	}
}
