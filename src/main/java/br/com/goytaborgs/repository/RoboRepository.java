package br.com.goytaborgs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goytaborgs.model.Robo;


public interface RoboRepository extends JpaRepository<Robo, Long>{
    
}
