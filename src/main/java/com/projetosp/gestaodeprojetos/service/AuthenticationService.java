package com.projetosp.gestaodeprojetos.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projetosp.gestaodeprojetos.infra.TokenService;
import com.projetosp.gestaodeprojetos.model.AuthenticationDTO;
import com.projetosp.gestaodeprojetos.model.LoginResponseDTO;
import com.projetosp.gestaodeprojetos.model.RegisterDTO;
import com.projetosp.gestaodeprojetos.model.Usuario;
import com.projetosp.gestaodeprojetos.repository.UsuarioRepository;



@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    TokenService tokenService;

    
    public LoginResponseDTO login(AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return new LoginResponseDTO(token);
    }

    
    public Usuario register(RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return new Usuario();
        String eP = new BCryptPasswordEncoder().encode(data.password());
        Usuario newUser = new Usuario(data.nome(), data.login(), eP, data.cpf(), data.telefone());
         newUser = repository.save(newUser);
        return newUser;
    }
}
