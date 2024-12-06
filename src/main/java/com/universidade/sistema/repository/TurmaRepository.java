package com.universidade.sistema.repository;

import com.universidade.sistema.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    @Query("SELECT t FROM Turma t WHERE t.ativo = true")
    List<Turma> findByAtivoTrue();

    @Query("SELECT t FROM Turma t WHERE t.ativo = false")
    List<Turma> findByAtivoFalse();

    @Query("SELECT DISTINCT t FROM Turma t " +
            "LEFT JOIN t.turmaDisciplinas td " +
            "LEFT JOIN td.disciplina d " +
            "LEFT JOIN td.turmaProfessores tp " +
            "LEFT JOIN tp.professor p " +
            "WHERE t.ativo = true " +
            "AND (:disciplinaId IS NULL OR d.id = :disciplinaId) " +
            "AND (:professorId IS NULL OR p.id = :professorId) " +
            "AND (:salaId IS NULL OR t.sala.id = :salaId)")
    List<Turma> findByDisciplinaIdAndProfessorIdAndSalaId(@Param("disciplinaId") Long disciplinaId,
                                                          @Param("professorId") Long professorId,
                                                          @Param("salaId") Long salaId);
}
