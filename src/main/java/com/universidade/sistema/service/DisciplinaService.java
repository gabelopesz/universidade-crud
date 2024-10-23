package com.universidade.sistema.service;

import com.universidade.sistema.model.Disciplina;
import com.universidade.sistema.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository repository;

    public List<Disciplina> listarAtivas() {
        return repository.findByAtivoTrue();
    }

    public List<Disciplina> listarInativas() {
        return repository.findByAtivoFalse();
    }

    public Disciplina salvar(Disciplina disciplina) {
        return repository.save(disciplina);
    }

    public void desativar(Long id) {
        Disciplina disciplina = repository.findById(id).orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
        disciplina.setAtivo(false);
        repository.save(disciplina);
    }

    public void reativar(Long id) {
        Disciplina disciplina = repository.findById(id).orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
        disciplina.setAtivo(true);
        repository.save(disciplina);
    }

    public Disciplina buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
    }
}

