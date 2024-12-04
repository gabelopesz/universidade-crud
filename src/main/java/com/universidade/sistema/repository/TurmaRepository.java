package com.universidade.sistema.repository;

import java.util.List;

import com.universidade.sistema.model.Sala;
import com.universidade.sistema.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    List<Turma> findByAtivoTrue();
    List<Turma> findByAtivoFalse();

    @Query("SELECT t FROM Turma t WHERE t.ativo = true AND "
            + "(:disciplinaId IS NULL OR :disciplinaId = 0 OR t.disciplina.id = :disciplinaId) AND "
            + "(:professorId IS NULL OR :professorId = 0 OR t.professor.id = :professorId) AND "
            + "(:salaId IS NULL OR :salaId = 0 OR t.sala.id = :salaId)")
    List<Turma> findByDisciplinaIdAndProfessorIdAndSalaId(@Param("disciplinaId") Long disciplinaId,
                                                          @Param("professorId") Long professorId,
                                                          @Param("salaId") Long salaId);

}

