package br.com.goytaborgs.model;

import java.util.Collection;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tgoytaborgs_membros")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String login;
    @Column(nullable = true)
    private String password;
    @Column
    private UsuarioRole role;
    @Column
    private String cpf;
    @Column
    private String telefone;
    @ManyToOne 
    @JoinColumn(name = "equipe_id")
    @JsonBackReference(value = "usuarios")
    private Equipe equipe;

    

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Usuario(String nome, String login, String password, String cpf, String telefone, Equipe equipe) {
        this.nome = nome;
        this.login = login;
        this.password = password;
        this.cpf = cpf;
        this.telefone = telefone;
        this.equipe = equipe;
        this.role = UsuarioRole.USER;
    }

    public Usuario(String nome, String login, String password, String cpf, String telefone) {
        this.nome = nome;
        this.login = login;
        this.password = password;
        this.cpf = cpf;
        this.telefone = telefone;
        this.role = UsuarioRole.USER;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    

    

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.login = email;
    }

    public void setSenha(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UsuarioRole role) {
        this.role = role;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public UsuarioRole getRole() {
        return role;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    // IMPLEMENTACAO USERDATAILS
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UsuarioRole.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {

        return password;
    }

    @Override
    public String getUsername() {

        return login;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }

}