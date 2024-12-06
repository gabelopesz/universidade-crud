package com.universidade.sistema.service;

import com.universidade.sistema.model.Turma;
import com.universidade.sistema.model.TurmaDisciplina;
import com.universidade.sistema.model.TurmaProfessor;
import com.universidade.sistema.repository.TurmaDisciplinaRepository;
import com.universidade.sistema.repository.TurmaProfessorRepository;
import com.universidade.sistema.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private TurmaProfessorRepository turmaProfessorRepository;

    @Autowired
    private TurmaDisciplinaRepository turmaDisciplinaRepository;

    public List<Turma> listarAtivas() {
        return turmaRepository.findByAtivoTrue();
    }

    public List<Turma> listarInativas() {
        return turmaRepository.findByAtivoFalse();
    }

    public Turma salvar(Turma turma) {
        // Configurar relação bidirecional com TurmaDisciplina
        if (turma.getTurmaDisciplinas() != null) {
            turma.getTurmaDisciplinas().forEach(td -> {
                td.setTurma(turma);

                // Configurar relação bidirecional entre TurmaProfessor e TurmaDisciplina
                if (td.getTurmaProfessores() != null) {
                    td.getTurmaProfessores().forEach(tp -> tp.setTurmaDisciplina(td));
                }
            });
        }

        return turmaRepository.save(turma);
    }

    public void desativar(Long id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        turma.setAtivo(false);

        // Desativar associações relacionadas
        List<TurmaDisciplina> turmaDisciplinas = turmaDisciplinaRepository.findByTurmaId(id);
        turmaDisciplinas.forEach(turmaDisciplina -> {
            // Deletar os professores vinculados às disciplinas
            List<TurmaProfessor> turmaProfessores = turmaProfessorRepository.findByTurmaDisciplinaId(turmaDisciplina.getId());
            turmaProfessorRepository.deleteAll(turmaProfessores);
            // Deletar a disciplina da turma
            turmaDisciplinaRepository.delete(turmaDisciplina);
        });

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

    // Métodos adicionais para manipular TurmaProfessor
    public List<TurmaProfessor> buscarProfessoresPorDisciplina(Long turmaDisciplinaId) {
        return turmaProfessorRepository.findByTurmaDisciplinaId(turmaDisciplinaId);
    }

    public void salvarTurmaProfessor(TurmaProfessor turmaProfessor) {
        turmaProfessorRepository.save(turmaProfessor);
    }

    public void deletarTurmaProfessor(Long turmaProfessorId) {
        turmaProfessorRepository.deleteById(turmaProfessorId);
    }

    // Métodos adicionais para manipular TurmaDisciplina
    public List<TurmaDisciplina> buscarDisciplinasPorTurma(Long turmaId) {
        return turmaDisciplinaRepository.findByTurmaId(turmaId);
    }

    public void salvarTurmaDisciplina(TurmaDisciplina turmaDisciplina) {
        turmaDisciplinaRepository.save(turmaDisciplina);
    }

    public void deletarTurmaDisciplina(Long turmaDisciplinaId) {
        turmaDisciplinaRepository.deleteById(turmaDisciplinaId);
    }
}
