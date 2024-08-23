package br.com.goytaborgs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.goytaborgs.DTO.CadastrarRoboDTO;
import br.com.goytaborgs.DTO.IntegrantesDTO;
import br.com.goytaborgs.model.Equipe;
import br.com.goytaborgs.model.Robo;
import br.com.goytaborgs.model.Usuario;
import br.com.goytaborgs.repository.EquipeRepository;
import br.com.goytaborgs.repository.RoboRepository;
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
        if (dto.categoria().equals("Cupim")) {
            List<Robo> listaDeRObos = roboRepository.findByCategoria("Cupim");
            if (listaDeRObos.size()>9) {
                return null;
            }
        } else {
            if (dto.categoria().equals("Seguidor de linha")) {
                List<Robo> listaDeRObos = roboRepository.findByCategoria("Seguidor de linha");
                if (listaDeRObos.size()>9) {
                    return null;
                }
            }
            else {
                if (dto.categoria().equals("Sumo mini auto")) {
                    List<Robo> listaDeRObos = roboRepository.findByCategoria("Sumo mini auto");
                    if (listaDeRObos.size()>9) {
                        return null;
                    }
                }
                else {
                    if (dto.categoria().equals("Fairyweight")) {
                        List<Robo> listaDeRObos = roboRepository.findByCategoria("Fairyweight");
                        if (listaDeRObos.size()>9) {
                            return null;
                        }
                    }
                }
            }
        }
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
        equipe.setNumeroderobos(equipe.getNumeroderobos()+1);
        equipeRepository.save(equipe);
        return robo;

    }
    public List<IntegrantesDTO> getAllRobos(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        Usuario user = authorizationService.findUserByToken(authHeader);
        Equipe equipe = user.getEquipe();
        List<IntegrantesDTO> integrantesDTO = new ArrayList<>();
        for (Robo u : equipe.getRobos()) {
            integrantesDTO.add(new IntegrantesDTO(u.getNome(), u.getCategoria()));
        }
        return integrantesDTO;

    }
}
