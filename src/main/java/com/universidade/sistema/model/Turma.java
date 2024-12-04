package com.universidade.sistema.model;

import jakarta.persistence.*;

@Entity
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    @ManyToOne
    @JoinColumn(name = "professor_id")  // Relacionamento correto com Professor
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;

    private String nome;

    private String dia_da_semana;

    private String horário_inicio;

    private String horário_termino;

    private boolean ativo = true;

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {this.nome = nome;}

    public String getDia_da_semana() {return dia_da_semana;}

    public void setDia_da_semana(String dia_da_semana) {this.dia_da_semana = dia_da_semana;}

    public String getHorário_inicio() {
        return horário_inicio;
    }

    public void setHorário_inicio(String horário_inicio) {
        this.horário_inicio = horário_inicio;
    }

    public String getHorário_termino() {
        return horário_termino;
    }

    public void setHorário_termino(String horário_termino) {
        this.horário_termino = horário_termino;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
