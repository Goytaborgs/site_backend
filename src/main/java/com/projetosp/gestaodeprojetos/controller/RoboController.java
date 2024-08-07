package com.projetosp.gestaodeprojetos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetosp.gestaodeprojetos.DTO.CadastrarRoboDTO;
import com.projetosp.gestaodeprojetos.model.Robo;
import com.projetosp.gestaodeprojetos.service.RoboService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("robo")
public class RoboController {
     @Autowired
    private RoboService roboService;

    @PostMapping
    public ResponseEntity<Robo> criarRobo(HttpServletRequest request, @RequestBody CadastrarRoboDTO dto) {
        Robo novoRobo = roboService.criarRobo(request, dto);
        if (novoRobo != null) {
            return ResponseEntity.ok(novoRobo);
        } else {
            return ResponseEntity.status(403).build(); 
        }
    }
}
