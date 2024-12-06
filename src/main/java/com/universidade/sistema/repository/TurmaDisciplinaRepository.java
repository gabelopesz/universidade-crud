package com.universidade.sistema.repository;

import com.universidade.sistema.model.TurmaDisciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurmaDisciplinaRepository extends JpaRepository<TurmaDisciplina, Long> {
    List<TurmaDisciplina> findByTurmaId(Long turmaId);
}
