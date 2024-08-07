package com.projetosp.gestaodeprojetos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetosp.gestaodeprojetos.DTO.CadastrarUserEquipe;
import com.projetosp.gestaodeprojetos.DTO.EquipeRequestDTO;
import com.projetosp.gestaodeprojetos.model.Equipe;
import com.projetosp.gestaodeprojetos.model.Robo;
import com.projetosp.gestaodeprojetos.model.Usuario;
import com.projetosp.gestaodeprojetos.repository.EquipeRepository;
import com.projetosp.gestaodeprojetos.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class EquipeService {
    
    @Autowired
    private EquipeRepository equipeRepository;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Equipe criarEquipe(HttpServletRequest request, EquipeRequestDTO dto){
        String authHeader = request.getHeader("Authorization");
        System.out.println("entrou");
        Usuario user = authorizationService.findUserByToken(authHeader);
        
        List<Robo> robos = new ArrayList<>();
        Equipe equipe = new Equipe();
        equipe.setInstituicao(dto.instituicao());
        equipe.setNomeequipe(dto.nomeequipe());
        equipe.setNumerodemembros(1);
        equipe.setRobos(robos);
        equipe.setCapitaoid(user.getId());
        List<Usuario> integrantes = new ArrayList<>();
        integrantes.add(user);
        equipe.setIntegrantes(integrantes);
        Equipe equipe2 = equipeRepository.save(equipe);
        user.setEquipe(equipe2);
        Usuario user99 = usuarioRepository.save(user);
        return equipe2;
    }
    public Equipe adicionarIntegrante(HttpServletRequest request, CadastrarUserEquipe dto) {
        String authHeader = request.getHeader("Authorization");
        Usuario user = authorizationService.findUserByToken(authHeader);
        Equipe equipe = user.getEquipe();
        Usuario userNovoIntegrante = usuarioRepository.findByLogin(dto.email());
        if(userNovoIntegrante==null){
            return null;
        }
        if (equipe.getCapitaoid()!=user.getId()) {
           return null;
        }
        
        List<Usuario> integrantes = equipe.getIntegrantes();
        integrantes.add(userNovoIntegrante);
        equipe.setIntegrantes(integrantes);
        equipe.setNumerodemembros(equipe.getNumerodemembros() + 1);
        return equipeRepository.save(equipe);
    }

}
