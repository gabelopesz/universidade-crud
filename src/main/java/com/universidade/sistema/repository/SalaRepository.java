package com.universidade.sistema.repository;

import java.util.List;
import com.universidade.sistema.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SalaRepository extends JpaRepository<Sala, Long> {
    List<Sala> findByAtivoTrue();
    List<Sala> findByAtivoFalse();

    @Query("SELECT s FROM Sala s WHERE LOWER(s.nome) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Sala> pesquisarPorNome(@Param("termo") String termo);
}
