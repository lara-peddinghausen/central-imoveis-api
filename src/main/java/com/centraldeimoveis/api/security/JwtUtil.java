package com.centraldeimoveis.api.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

  // Chave secreta: mínimo 256 bits para HMAC-SHA256
  @Value("${jwt.secret}")
  private String secret;

  // Tempo de validade: 24 horas em milissegundos
  private static final long EXPIRATION = 86_400_000L;

  // Converte a string da chave para um objeto SecretKey
  private SecretKey getKey() {
    return Keys.hmacShaKeyFor(
        Decoders.BASE64.decode(secret)
    );
  }

  // Gera um token JWT para o email fornecido
  public String generateToken(String email) {
    return Jwts.builder()
        .subject(email)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
        .signWith(getKey())
        .compact();
  }

  // Extrai o email (subject) de um token
  public String extractEmail(String token) {
    return Jwts.parser()
        .verifyWith(getKey())
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }

  // Retorna true se o token ainda não expirou e o email confere
  public boolean isValid(String token, UserDetails userDetails) {
    try {
      String email = extractEmail(token);
      return email.equals(userDetails.getUsername());
      // JJWT lança exceção se o token estiver expirado
    } catch (JwtException e) {
      return false;
    }
  }
}
