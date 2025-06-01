package com.octadata.pontolite.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class WebSecurityConfig{

	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests()
			.anyRequest().permitAll()
			.and().formLogin(form -> form.loginPage("/pontolite").permitAll().defaultSuccessUrl("/pontolite", true))
	        .exceptionHandling()
	        .accessDeniedHandler(accessDeniedHandler())
	        .and()
			.logout(logout -> logout.logoutUrl("/logout"))
			.csrf().disable();

		System.out.println(passwordEncoder().encode("12345"));
		return http.build();
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
	   return new CustomAccessDeniedHandler();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
