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
import br.com.goytaborgs.DTO.EmailDTO;
import br.com.goytaborgs.DTO.LoginResponseDTO;
import br.com.goytaborgs.DTO.RegisterDTO;
import br.com.goytaborgs.DTO.ResetPasswordDTO;
import br.com.goytaborgs.infra.TokenService;
import br.com.goytaborgs.model.Usuario;
import br.com.goytaborgs.repository.EquipeRepository;
import br.com.goytaborgs.repository.UsuarioRepository;
import br.com.goytaborgs.service.EmailService;
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
    @Autowired
    private EmailService emailService;

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
    @PostMapping("/forgot-password")
     public ResponseEntity<String> forgotPassword(@RequestBody @Valid EmailDTO emailDTO) {
         Usuario optionalUser = repository.findByLogin(emailDTO.email());
         if (optionalUser!=null) {
             Usuario user = optionalUser;
             String token = tokenService.generateToken(user);
             String resetLink = "https://goytaborgs.com.br/reset-password.html?token=" + token;
             emailService.enviaEmail(user.getUsername(), resetLink, user.getNome());
             
             return ResponseEntity.ok("Te enviamos um link para mudar a senha por email.");
         } else {
             return ResponseEntity.status(404).body("Seu email não foi encontrado na nossa base de dados");
         }
     }
     @PostMapping("/reset-password")
     public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPasswordDTO resetPasswordDTO) {
         String username = tokenService.validateToken(resetPasswordDTO.token());
         if (username==null) {
             return ResponseEntity.status(401).body("Seu link expirou");
         }

         Usuario optionalUser = repository.findByLogin(username);
         if (optionalUser!=null) {
             Usuario user = optionalUser;
             String encodedPassword = new BCryptPasswordEncoder().encode(resetPasswordDTO.newPassword());      
             user.setPassword(encodedPassword);
             repository.save(user);

             return ResponseEntity.ok("Sua senha foi alterada com sucesso!");
         } else {
             return ResponseEntity.status(404).body("Não foi possível alterar sua senha, tente novamente.");
         }
     }
}