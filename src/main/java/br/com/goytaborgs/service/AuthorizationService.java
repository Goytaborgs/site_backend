package br.com.goytaborgs.service;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.goytaborgs.infra.TokenService;
import br.com.goytaborgs.model.Usuario;
import br.com.goytaborgs.repository.UsuarioRepository;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UsuarioRepository repository;
    @Autowired
    private TokenService tokenService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }
    public Usuario findUserByToken (String token){
    	try {
    		
         if (token == null || !token.startsWith("Bearer ")) {
                return null;
         }
    	String newToken = token.substring(7);
    	String username = tokenService.validateToken(newToken);
    	Usuario usuario = repository.findByLogin(username);
    	return usuario;
    	} catch (Exception e) {
    		return null;
    	}
    }
}
