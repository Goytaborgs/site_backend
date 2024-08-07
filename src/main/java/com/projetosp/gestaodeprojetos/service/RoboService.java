package com.projetosp.gestaodeprojetos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetosp.gestaodeprojetos.DTO.CadastrarRoboDTO;
import com.projetosp.gestaodeprojetos.model.Equipe;
import com.projetosp.gestaodeprojetos.model.Robo;
import com.projetosp.gestaodeprojetos.model.Usuario;
import com.projetosp.gestaodeprojetos.repository.EquipeRepository;
import com.projetosp.gestaodeprojetos.repository.RoboRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class RoboService {

    @Autowired
    private RoboRepository roboRepository;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private EquipeRepository equipeRepository;

    public Robo criarRobo(HttpServletRequest request, CadastrarRoboDTO dto) {
        String authHeader = request.getHeader("Authorization");
        Usuario user = authorizationService.findUserByToken(authHeader);
        Equipe equipe = user.getEquipe();
        if (equipe.getCapitaoid() != user.getId()) {
            return null;
        }
        Robo robo= new Robo(dto.nome(), dto.categoria(), dto.peso(), equipe);
        roboRepository.save(robo);
        List<Robo> robos = equipe.getRobos();
        robos.add(robo);
        equipe.setRobos(robos);
        equipeRepository.save(equipe);
        return robo;

    }
}
