package com.universidade.sistema.service;

import com.universidade.sistema.model.Aluno;
import com.universidade.sistema.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    // Busca todos os alunos
    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    // Busca um aluno pelo ID
    public Aluno findById(Long id) {
        return alunoRepository.findById(id).orElse(null);
    }

    // Salva ou atualiza um aluno
    public Aluno save(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    // Exclui um aluno pelo ID
    public void deleteById(Long id) {
        alunoRepository.deleteById(id);
    }
}
