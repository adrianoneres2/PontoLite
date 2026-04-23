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

import com.octadata.pontolite.controller.LoginController;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class WebSecurityConfig {

    private final LoginController loginController;

    WebSecurityConfig(LoginController loginController) {
        this.loginController = loginController;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/webjars/jquery/**", "/webjars/bootstrap/**", "/static/**", "/js/**",
                                "/css/**", "/fontes/**", "/images/**")
                        .permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/pontolite/ponto/registrarPonto").hasAnyRole("REGISTRAR_PONTO")
                        .requestMatchers("/pontolite/acessarRegistroPonto").hasRole("ACESSAR_REGISTRO_PONTO")
                        .requestMatchers("/pontolite/cliente/formulario").hasRole("CADASTRAR_CLIENTE")
                        .requestMatchers("/pontolite/cliente/listar").hasRole("LISTAR_CLIENTE")
                        .requestMatchers("/pontolite/cliente/alterar-status/{codCliente}")
                        .hasRole("ALTERAR_STATUS_CLIENTE")
                        .requestMatchers("/pontolite/cliente/alterar").hasRole("ALTERAR_CLIENTE")
                        .requestMatchers("/pontolite/usuario/listar").hasRole("LISTAR_USUARIO")
                        .requestMatchers("/pontolite/usuario/formulario").hasRole("CADASTRAR_USUARIO")
                        .requestMatchers("/pontolite/usuario/alterar").hasRole("ALTERAR_USUARIO")
                        .requestMatchers("/pontolite/perfil/api/listar-configuracoes").hasRole("LISTAR_PERFIL")
                        .requestMatchers("/pontolite/perfil/api/salvar-configuracao").hasRole("CADASTRAR_PERFIL")
                        .requestMatchers("/pontolite/perfil/acessar-formulario").hasRole("ACESSAR_PERFIL")
                        .requestMatchers("/pontolite/jornada/listar").hasRole("LISTAR_JORNADA")
                        .requestMatchers("/pontolite/jornada/formulario").hasRole("CADASTRAR_JORNADA")
                        .requestMatchers("/pontolite/jornada/alterar").hasRole("ALTERAR_JORNADA")
                        .requestMatchers("/pontolite/jornada/alterar-status/{codJornada}")
                        .hasRole("ALTERAR_STATUS_JORNADA")
                        .requestMatchers("/pontolite/jornada/quadro-horarios").hasRole("ACESSAR_QUADRO_HORARIO_JORNADA")
                        .anyRequest()
                        .authenticated())
                .formLogin(form -> form.loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/pontolite", true)
                        .permitAll())
                .exceptionHandling(handling -> handling
                        .accessDeniedHandler(accessDeniedHandler()).accessDeniedPage("/login/accessDenied"))
                .logout(logout -> logout.logoutUrl("/login/logout").permitAll())
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
