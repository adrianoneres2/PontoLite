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
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                		.requestMatchers("/webjars/jquery/**", "/webjars/bootstrap/**").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/pontolite/ponto/registrarPonto").hasAnyRole("REGISTRAR_PONTO")
                        .requestMatchers("/pontolite/acessarRegistroPonto").hasRole("ACESSAR_REGISTRO_PONTO")
                        .anyRequest()
                        .authenticated()
                        )
                .formLogin(form -> form.loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/pontolite", true)
                        .permitAll()
                        )
                .exceptionHandling(handling -> handling
                        .accessDeniedHandler(accessDeniedHandler()).accessDeniedPage("/login")
                        )
                .logout(logout -> logout.logoutUrl("/login/logout").permitAll()
                )
                .csrf(csrf -> csrf.disable());

		System.out.println(passwordEncoder().encode("12345"));
		return http.build();
	}

    @Bean
    AccessDeniedHandler accessDeniedHandler() {
	   return new CustomAccessDeniedHandler();
	}

    @Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
