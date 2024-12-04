package com.universidade.sistema.repository;

import java.util.List;
import com.universidade.sistema.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    List<Professor> findByAtivoTrue();
    List<Professor> findByAtivoFalse();

    @Query("SELECT p FROM Professor p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Professor> pesquisarPorNome(@Param("termo") String termo);
}
