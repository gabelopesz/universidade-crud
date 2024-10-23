package com.universidade.sistema.service;

import com.universidade.sistema.model.Professor;
import com.universidade.sistema.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository repository;

    public List<Professor> listarAtivos() {
        return repository.findByAtivoTrue();
    }

    public List<Professor> listarInativos() {
        return repository.findByAtivoFalse();
    }

    public Professor salvar(Professor professor) {
        return repository.save(professor);
    }

    public void desativar(Long id) {
        Professor professor = repository.findById(id).orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        professor.setAtivo(false);
        repository.save(professor);
    }

    public void reativar(Long id) {
        Professor professor = repository.findById(id).orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        professor.setAtivo(true);
        repository.save(professor);
    }

    public Professor buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Professor não encontrado"));
    }
}
