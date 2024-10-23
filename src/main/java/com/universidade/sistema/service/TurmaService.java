package com.universidade.sistema.service;

import com.universidade.sistema.model.Turma;
import com.universidade.sistema.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository repository;

    public List<Turma> listarAtivas() {
        return repository.findByAtivoTrue();
    }

    public List<Turma> listarInativas() {
        return repository.findByAtivoFalse();
    }

    public Turma salvar(Turma turma) {
        return repository.save(turma);
    }

    public void desativar(Long id) {
        Turma turma = repository.findById(id).orElseThrow(() -> new RuntimeException("Turma não encontrada"));
        turma.setAtivo(false);
        repository.save(turma);
    }

    public void reativar(Long id) {
        Turma turma = repository.findById(id).orElseThrow(() -> new RuntimeException("Turma não encontrada"));
        turma.setAtivo(true);
        repository.save(turma);
    }

    public Turma buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Turma não encontrada"));
    }
}
