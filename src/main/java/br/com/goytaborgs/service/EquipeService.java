package br.com.goytaborgs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.goytaborgs.DTO.CadastrarUserEquipe;
import br.com.goytaborgs.DTO.EquipeRequestDTO;
import br.com.goytaborgs.DTO.IntegrantesDTO;
import br.com.goytaborgs.model.Equipe;
import br.com.goytaborgs.model.Robo;
import br.com.goytaborgs.model.Usuario;
import br.com.goytaborgs.repository.EquipeRepository;
import br.com.goytaborgs.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class EquipeService {

    @Autowired
    private EquipeRepository equipeRepository;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Equipe criarEquipe(HttpServletRequest request, EquipeRequestDTO dto) {
        String authHeader = request.getHeader("Authorization");
        Usuario user = authorizationService.findUserByToken(authHeader);

        List<Robo> robos = new ArrayList<>();
        Equipe equipe = new Equipe();
        equipe.setInstituicao(dto.instituicao());
        equipe.setNomeequipe(dto.nomeequipe());
        equipe.setNumerodemembros(1);
        equipe.setNumeroderobos(0);
        equipe.setRobos(robos);
        equipe.setCapitaoid(user.getId());
        List<Usuario> integrantes = new ArrayList<>();
        integrantes.add(user);
        equipe.setIntegrantes(integrantes);
        Equipe equipe2 = equipeRepository.save(equipe);
        user.setEquipe(equipe2);
        usuarioRepository.save(user);
        return equipe2;
    }

    public Equipe adicionarIntegrante(HttpServletRequest request, CadastrarUserEquipe dto) {
        String authHeader = request.getHeader("Authorization");
        Usuario user = authorizationService.findUserByToken(authHeader);
        Equipe equipe = user.getEquipe();
        Usuario userNovoIntegrante = usuarioRepository.findByLogin(dto.email());
        if (userNovoIntegrante == null) {
            return null;
        }
        if (equipe.getCapitaoid() != user.getId()) {
            return null;
        }

        List<Usuario> integrantes = equipe.getIntegrantes();
        userNovoIntegrante.setEquipe(equipe);
        usuarioRepository.save(userNovoIntegrante);
        integrantes.add(userNovoIntegrante);
        equipe.setIntegrantes(integrantes);
        equipe.setNumerodemembros(equipe.getNumerodemembros() + 1);
        return equipeRepository.save(equipe);
    }

    public List<IntegrantesDTO> getAllIntegrantes(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        Usuario user = authorizationService.findUserByToken(authHeader);
        Equipe equipe = user.getEquipe();
        List<IntegrantesDTO> integrantesDTO = new ArrayList<>();
        for (Usuario u : equipe.getIntegrantes()) {
            System.out.println(u.getNome());
            integrantesDTO.add(new IntegrantesDTO(u.getNome(), u.getUsername()));

        }
        return integrantesDTO;

    }
}
