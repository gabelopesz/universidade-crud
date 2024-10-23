package com.universidade.sistema.repository;

import java.util.List;
import com.universidade.sistema.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    List<Professor> findByAtivoTrue();
    List<Professor> findByAtivoFalse();
}
