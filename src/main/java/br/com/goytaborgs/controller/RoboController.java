package br.com.goytaborgs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goytaborgs.DTO.CadastrarRoboDTO;
import br.com.goytaborgs.DTO.IntegrantesDTO;
import br.com.goytaborgs.model.Robo;
import br.com.goytaborgs.service.RoboService;
import jakarta.servlet.http.HttpServletRequest;
@CrossOrigin(origins = "*")
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
    @GetMapping
    public List<IntegrantesDTO> getRobos(HttpServletRequest request) {
        return roboService.getAllRobos(request);
    }
}
