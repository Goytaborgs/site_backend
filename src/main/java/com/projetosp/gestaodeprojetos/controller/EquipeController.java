package com.projetosp.gestaodeprojetos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetosp.gestaodeprojetos.DTO.CadastrarUserEquipe;
import com.projetosp.gestaodeprojetos.DTO.EquipeRequestDTO;
import com.projetosp.gestaodeprojetos.model.Equipe;
import com.projetosp.gestaodeprojetos.service.EquipeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/equipe")
public class EquipeController {

    @Autowired
    private EquipeService equipeService;

    @PostMapping
    public ResponseEntity<Equipe> criarEquipe(HttpServletRequest request, @RequestBody EquipeRequestDTO dto) {
        Equipe novaEquipe = equipeService.criarEquipe(request, dto);
        return ResponseEntity.ok(novaEquipe);
    }

    @PostMapping("/adicionar-integrante")
    public ResponseEntity<Equipe> adicionarIntegrante(HttpServletRequest request, @RequestBody CadastrarUserEquipe dto) {
        Equipe equipeAtualizada = equipeService.adicionarIntegrante(request, dto);
        if (equipeAtualizada != null) {
            return ResponseEntity.ok(equipeAtualizada);
        } else {
            return ResponseEntity.status(403).build(); 
        }
    }
}
