package br.com.goytaborgs.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tgoytaborgs_robos")
@SequenceGenerator(name= "generator_robos", sequenceName="sequence_robos", initialValue= 1, allocationSize= 1)
public class Robo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_robos")
    private Long id;
    @Column
    private String nome;
    @Column
    private String categoria;
    @Column
    private Double peso;
    @ManyToOne
    @JoinColumn(name = "equipe_id")
    @JsonBackReference(value = "robos")
    private Equipe equipe;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public Double getPeso() {
        return peso;
    }
    public void setPeso(Double peso) {
        this.peso = peso;
    }
    public Equipe getEquipe() {
        return equipe;
    }
    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }
   
    
    public Robo(String nome, String categoria, Double peso, Equipe equipe) {
        this.nome = nome;
        this.categoria = categoria;
        this.peso = peso;
        this.equipe = equipe;
    }
    
}
