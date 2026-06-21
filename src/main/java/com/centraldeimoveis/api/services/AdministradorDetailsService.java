package com.centraldeimoveis.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.centraldeimoveis.api.repository.AdministradorRepository;

@Service
public class AdministradorDetailsService implements UserDetailsService {

    @Autowired
    private AdministradorRepository administradorRepository;

@Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    
// Busca o administrador de forma explícita
com.centraldeimoveis.api.model.administrador.Administrador administrador = administradorRepository
    .findByEmail(email)
    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email));
    
// Retorna a variável configurada
return administrador;
}
}