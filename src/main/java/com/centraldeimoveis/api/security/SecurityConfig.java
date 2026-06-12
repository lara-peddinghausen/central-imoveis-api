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

import com.centraldeimoveis.api.service.AdministradorDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired private AdministradorDetailsService administradorDetailsService;
  @Autowired private JwtAuthFilter jwtAuthFilter;

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
        // 1. Desativa CSRF — APIs REST não usam cookies de sessão
        .csrf(csrf -> csrf.disable())

        // 2. Configura a sessão como stateless — o servidor não guarda estado
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )

        // 3. Regras de acesso por rota (Liberando login e cadastro inicial)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(org.springframework.http.HttpMethod.POST, "/auth/login").permitAll()       // Login público da apostila
            .requestMatchers(org.springframework.http.HttpMethod.POST, "/administrador").permitAll() // 🚀 Cadastro público do seu AdministradorController
            .anyRequest().authenticated()                                                              // Todo o resto exige o token JWT
        )

        // 4. Adiciona o filtro JWT antes do filtro padrão de usuário/senha (Sintaxe Corrigida ✅)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

        // 5. Constrói e retorna a cadeia de filtros configurada
        .build();
}

}
