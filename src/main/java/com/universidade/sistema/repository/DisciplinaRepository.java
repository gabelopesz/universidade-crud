package com.universidade.sistema.repository;

import java.util.List;
import com.universidade.sistema.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    List<Disciplina> findByAtivoTrue();

    List<Disciplina> findByAtivoFalse();
}


