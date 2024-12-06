package com.universidade.sistema.repository;

import com.universidade.sistema.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    List<Turma> findByAtivoTrue();
    List<Turma> findByAtivoFalse();

    @Query("SELECT DISTINCT t FROM Turma t " +
            "LEFT JOIN TurmaDisciplina td ON t.id = td.turma.id " +
            "LEFT JOIN TurmaProfessor tp ON t.id = tp.turma.id " +
            "WHERE t.ativo = true AND " +
            "(:disciplinaId IS NULL OR td.disciplina.id = :disciplinaId) AND " +
            "(:professorId IS NULL OR tp.professor.id = :professorId) AND " +
            "(:salaId IS NULL OR t.sala.id = :salaId)")
    List<Turma> findByDisciplinaIdAndProfessorIdAndSalaId(
            @Param("disciplinaId") Long disciplinaId,
            @Param("professorId") Long professorId,
            @Param("salaId") Long salaId);
}
