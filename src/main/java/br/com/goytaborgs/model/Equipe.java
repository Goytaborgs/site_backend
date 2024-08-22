package br.com.goytaborgs.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tgoytaborgs_equipes")
@SequenceGenerator(name= "generator_equipe", sequenceName="sequence_equipe", initialValue= 1, allocationSize= 1)
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_equipe")
    private Long id;
    @Column
    private String instituicao;
    @Column
    private String nomeequipe;
    @OneToMany(mappedBy = "equipe")
    @JsonManagedReference(value = "usuarios")
    private List<Usuario> integrantes;
    @Column
    private long capitaoid;
    @Column
    private int numerodemembros;
    @OneToMany(mappedBy = "equipe")
    @JsonManagedReference(value = "robos")
    private List<Robo> robos;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getInstituicao() {
        return instituicao;
    }
    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }
    public String getNomeequipe() {
        return nomeequipe;
    }
    public void setNomeequipe(String nomeequipe) {
        this.nomeequipe = nomeequipe;
    }
    public List<Usuario> getIntegrantes() {
        return integrantes;
    }
    public void setIntegrantes(List<Usuario> integrantes) {
        this.integrantes = integrantes;
    }
    public long getCapitaoid() {
        return capitaoid;
    }
    public void setCapitaoid(long capitaoid) {
        this.capitaoid = capitaoid;
    }
    public int getNumerodemembros() {
        return numerodemembros;
    }
    public void setNumerodemembros(int numerodemembros) {
        this.numerodemembros = numerodemembros;
    }
    public List<Robo> getRobos() {
        return robos;
    }
    public void setRobos(List<Robo> robos) {
        this.robos = robos;
    }
    public Equipe(String instituicao, String nomeequipe, List<Usuario> integrantes, long capitaoid, int numerodemembros,
            List<Robo> robos) {
        this.instituicao = instituicao;
        this.nomeequipe = nomeequipe;
        this.integrantes = integrantes;
        this.capitaoid = capitaoid;
        this.numerodemembros = numerodemembros;
        this.robos = robos;
    }


    

}
