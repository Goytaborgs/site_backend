package br.com.goytaborgs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.goytaborgs.model.Equipe;


@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long>{
    
}
