package com.universidade.sistema.repository;

import java.util.List;
import com.universidade.sistema.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    List<Turma> findByAtivoTrue();
    List<Turma> findByAtivoFalse();
}
