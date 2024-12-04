package com.universidade.sistema.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.universidade.sistema.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    List<Disciplina> findByAtivoTrue();

    List<Disciplina> findByAtivoFalse();

    @Query("SELECT d FROM Disciplina d WHERE LOWER(d.nome) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Disciplina> pesquisarPorNome(@Param("termo") String termo);

}


