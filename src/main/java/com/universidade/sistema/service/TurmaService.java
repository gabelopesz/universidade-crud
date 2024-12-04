package com.universidade.sistema.service;

import com.universidade.sistema.model.Turma;
import com.universidade.sistema.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    public List<Turma> listarAtivas() {
        return turmaRepository.findByAtivoTrue();
    }

    public List<Turma> listarInativas() {
        return turmaRepository.findByAtivoFalse();
    }

    public Turma salvar(Turma turma) {
        return turmaRepository.save(turma);
    }

    public void desativar(Long id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
        turma.setAtivo(false);
        turmaRepository.save(turma);
    }

    public void reativar(Long id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
        turma.setAtivo(true);
        turmaRepository.save(turma);
    }

    public Turma buscarPorId(Long id) {
        return turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
    }
    public List<Turma> pesquisarTurmas(Long disciplinaId, Long professorId, Long salaId) {
        return turmaRepository.findByDisciplinaIdAndProfessorIdAndSalaId(disciplinaId, professorId, salaId);
    }
}