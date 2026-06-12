package com.centraldeimoveis.api.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.centraldeimoveis.api.service.AdministradorDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter
    extends OncePerRequestFilter {   // executa exatamente 1x por request

  @Autowired private JwtUtil jwtUtil;
  @Autowired private AdministradorDetailsService administradorDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    // 1. Lê o header "Authorization: Bearer <token>"
    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      // Sem token → passa para o próximo filtro sem autenticar
      filterChain.doFilter(request, response);
      return;
    }

    // 2. Remove o prefixo "Bearer " e extrai o token puro
    String token = authHeader.substring(7);
    String email = jwtUtil.extractEmail(token);

    // 3. Autentica apenas se ainda não estiver autenticado nesta request
    if (email != null &&
        SecurityContextHolder.getContext().getAuthentication() == null) {

      UserDetails userDetails =
          administradorDetailsService.loadUserByUsername(email);

      if (jwtUtil.isValid(token, userDetails)) {
        // 4. Cria o objeto de autenticação e coloca no contexto
        UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
            );
        auth.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }

    // 5. Continua a cadeia de filtros
    filterChain.doFilter(request, response);
  }
}
