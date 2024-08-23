package br.com.goytaborgs.controller;







import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.goytaborgs.model.Equipe;
import br.com.goytaborgs.DTO.AuthenticationDTO;
import br.com.goytaborgs.DTO.LoginResponseDTO;
import br.com.goytaborgs.DTO.RegisterDTO;
import br.com.goytaborgs.infra.TokenService;
import br.com.goytaborgs.model.Usuario;
import br.com.goytaborgs.repository.EquipeRepository;
import br.com.goytaborgs.repository.UsuarioRepository;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    TokenService tokenService;
    @Autowired
    private EquipeRepository equipeRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        Usuario user = (Usuario) auth.getPrincipal();
        List<Equipe> equipes = equipeRepository.findAll();
        Boolean isCapitao = false;
        for(Equipe eqp: equipes) {
            if (eqp.getCapitaoid()==user.getId()) {
                isCapitao = true;
            }
        }
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token, isCapitao));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody @Valid RegisterDTO data){
        List<Usuario> usuariosList = repository.findAll();
        if (usuariosList.size()>119) {
            return ResponseEntity.badRequest().body(null);
        }
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().body(null);

        String eP = new BCryptPasswordEncoder().encode(data.password());
        Usuario newUser = new Usuario(data.nome(), data.login(), eP , data.cpf(), data.telefone());

        repository.save(newUser);
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token, false));
    }
}