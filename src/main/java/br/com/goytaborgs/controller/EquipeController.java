package br.com.goytaborgs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goytaborgs.DTO.CadastrarUserEquipe;
import br.com.goytaborgs.DTO.EquipeRequestDTO;
import br.com.goytaborgs.DTO.IntegrantesDTO;
import br.com.goytaborgs.model.Equipe;
import br.com.goytaborgs.service.EquipeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    @GetMapping("/integrantes")
    public List<IntegrantesDTO> getIntegrantes(HttpServletRequest request) {
        return equipeService.getAllIntegrantes(request);
    }
    
}
