package com.centraldeimoveis.api.model.administrador;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.centraldeimoveis.api.dto.administrador.DadosAtualizaAdministrador;
import com.centraldeimoveis.api.dto.administrador.DadosCadastroAdministrador;

// import java.util.List;
// import com.centraldeimoveis.api.model.imovel.Imovel;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa um administrador do sistema de central de imóveis.
 * 
 * Contém informações pessoais e credenciais de acesso, como nome, e-mail, senha, CPF e data de nascimento.
 * 
 * @author Lara Peddinghausen
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Data
@Table(name = "administrador")
public class Administrador implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String senha;

    @Column(nullable = false, unique = true, length = 150)
    private String email;
    
    @Column(nullable = false, length = 150)
    private String nome;
    
    private LocalDate dataNascimento;

    @Column(unique = true, length = 11)
    private String cpf;

    // @OneToMany(mappedBy = "administrador")
    // private List<Imovel> imoveis;

    /**
     * Cria um administrador a partir dos dados de cadastro.
     *
     * @param dados objeto contendo as informações necessárias para criação do administrador
     */
    public Administrador(DadosCadastroAdministrador dados) {
        this.senha = dados.senha();
        this.email = dados.email();
        this.nome = dados.nome();
        this.dataNascimento = dados.dataNascimento();
        this.cpf = dados.cpf();
    }

    /**
     * Atualiza os dados do administrador com base nas informações fornecidas.
     * Apenas os campos não nulos serão atualizados.
     *
     * @param dados objeto com os dados para atualização
     */
    public void atualizarAdministrador(DadosAtualizaAdministrador dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.senha() != null) {
            this.senha = dados.senha();
        }
        if (dados.dataNascimento() != null) {
            this.dataNascimento = dados.dataNascimento();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna ROLE_ADMIN direto em memória, economizando uma coluna no seu banco
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword() { return this.senha; }

    @Override
    public String getUsername() { return this.email; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

}
