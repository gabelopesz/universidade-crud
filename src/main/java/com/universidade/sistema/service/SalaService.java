package com.universidade.sistema.service;

import com.universidade.sistema.model.Sala;
import com.universidade.sistema.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {

    @Autowired
    private SalaRepository repository;

    public List<Sala> listarAtivas() {
        return repository.findByAtivoTrue();
    }

    public List<Sala> listarInativas() {
        return repository.findByAtivoFalse();
    }

    public Sala salvar(Sala sala) {
        return repository.save(sala);
    }

    public void desativar(Long id) {
        Sala sala = repository.findById(id).orElseThrow(() -> new RuntimeException("Sala não encontrada"));
        sala.setAtivo(false);
        repository.save(sala);
    }

    public void reativar(Long id) {
        Sala sala = repository.findById(id).orElseThrow(() -> new RuntimeException("Sala não encontrada"));
        sala.setAtivo(true);
        repository.save(sala);
    }

    public Sala buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Sala não encontrada"));
    }
}
