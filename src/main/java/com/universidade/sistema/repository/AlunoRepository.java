package com.universidade.sistema.repository;

import com.universidade.sistema.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByAtivoTrue();

    List<Aluno> findByAtivoFalse();

    List<Aluno> findByNomeContainingIgnoreCaseAndAtivoTrue(String termo);

    List<Aluno> findByNomeContainingIgnoreCaseAndAtivoFalse(String termo);
}
