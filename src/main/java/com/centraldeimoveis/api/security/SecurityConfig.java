package com.centraldeimoveis.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private JwtAuthFilter jwtAuthFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
      throws Exception {

    return http
        // Desativa CSRF — APIs REST não usam cookies de sessão
        .csrf(csrf -> csrf.disable())

        // Configura a sessão como stateless —> o servidor não guarda estado
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // Regras de acesso por rota
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(org.springframework.http.HttpMethod.POST, "/auth/login").permitAll() // Login público
            .requestMatchers(org.springframework.http.HttpMethod.POST, "/administrador").permitAll() // Cadastro público do AdministradorController
            .requestMatchers("/uploads/**").permitAll() // Atualizar conforme pasta usada para salvar imagens
            .requestMatchers("/imovel", "/imovel/**").hasRole("ADMIN")
            .anyRequest().authenticated() // Todo o resto exige o token JWT
        )

        // Adiciona o filtro JWT antes do filtro padrão de usuário/senha
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

        // Constrói e retorna a cadeia de filtros configurada
        .build();
  }

}
