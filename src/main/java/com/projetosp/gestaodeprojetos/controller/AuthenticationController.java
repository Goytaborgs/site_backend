package com.projetosp.gestaodeprojetos.controller;



import com.projetosp.gestaodeprojetos.model.Usuario;
import com.projetosp.gestaodeprojetos.repository.UsuarioRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.projetosp.gestaodeprojetos.model.LoginResponseDTO;
import com.projetosp.gestaodeprojetos.model.AuthenticationDTO;
import com.projetosp.gestaodeprojetos.model.RegisterDTO;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository repository;
    @Autowired
   com.projetosp.gestaodeprojetos.infra.TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public Usuario register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return new Usuario();

        String eP = new BCryptPasswordEncoder().encode(data.password());
        Usuario newUser = new Usuario(data.nome(), data.login(), eP , data.cpf(), data.telefone());

        repository.save(newUser);

        return newUser;
    }
}