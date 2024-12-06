package com.universidade.sistema.repository;

import com.universidade.sistema.model.TurmaProfessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TurmaProfessorRepository extends JpaRepository<TurmaProfessor, Long> {

    @Query("SELECT tp FROM TurmaProfessor tp WHERE tp.turmaDisciplina.turma.id = :turmaId")
    List<TurmaProfessor> findByTurmaId(@Param("turmaId") Long turmaId);

    List<TurmaProfessor> findByTurmaDisciplinaId(Long turmaDisciplinaId);
}
