package br.com.goytaborgs.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import br.com.goytaborgs.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
 Usuario findByLogin(String login);
    
    
}
