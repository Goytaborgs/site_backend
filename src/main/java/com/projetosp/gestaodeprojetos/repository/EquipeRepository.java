package com.projetosp.gestaodeprojetos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetosp.gestaodeprojetos.model.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long>{
    
}
