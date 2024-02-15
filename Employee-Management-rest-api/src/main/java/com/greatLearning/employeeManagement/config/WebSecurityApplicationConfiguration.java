package com.greatLearning.employeeManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityApplicationConfiguration {

	private final UserDetailsService userDetailsService;

	@Bean
	PasswordEncoder passwordEncoder() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}

	// authorization
	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authz -> authz.requestMatchers("/**", "/login**", "/logout**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/employees**").hasAnyRole("USER", "ADMIN")
				.requestMatchers(HttpMethod.GET, "/api/employees/search**").hasAnyRole("USER", "ADMIN")
				.requestMatchers(HttpMethod.POST, "/api/employees**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN").anyRequest()
				.fullyAuthenticated());
		http.cors(c -> c.disable());
		http.csrf(csrf -> csrf.disable());
		http.httpBasic(Customizer.withDefaults());
		http.formLogin(Customizer.withDefaults());
		http.headers(customizer -> customizer.frameOptions(o -> o.disable()));
		return http.build();
	}

	@Bean
	AuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEncoder());
		authProvider.setUserDetailsService(this.userDetailsService);
		return authProvider;
	}

}
