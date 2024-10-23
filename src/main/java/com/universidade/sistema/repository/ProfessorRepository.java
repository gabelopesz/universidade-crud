package com.universidade.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.universidade.sistema.model.Professor;
import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    List<Professor> findByAtivoTrue();
    List<Professor> findByAtivoFalse();
}
