package com.centraldeimoveis.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 🚀 Imports corrigidos e limpos para a nova estrutura de pastas: 
import com.centraldeimoveis.api.repository.AdministradorRepository;

@Service
public class AdministradorDetailsService implements UserDetailsService {

    @Autowired
    private AdministradorRepository administradorRepository;

@Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    
// 1. Busca o administrador de forma explícita (Adicionado "administrador =")
com.centraldeimoveis.api.model.administrador.Administrador administrador = administradorRepository
    .findByEmail(email)
    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email));
    
// 2. Retorna a variável configurada
return administrador;
}
}