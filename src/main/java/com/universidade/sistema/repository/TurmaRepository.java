package com.universidade.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.universidade.sistema.model.Turma;
import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    List<Turma> findByAtivoTrue();
    List<Turma> findByAtivoFalse();
}
