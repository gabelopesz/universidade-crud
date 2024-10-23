package com.universidade.sistema.repository;

import java.util.List;
import com.universidade.sistema.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {
    List<Sala> findByAtivoTrue();
    List<Sala> findByAtivoFalse();
}
