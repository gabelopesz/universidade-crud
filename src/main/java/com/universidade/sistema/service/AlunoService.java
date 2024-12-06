package com.universidade.sistema.service;

import com.universidade.sistema.model.Aluno;
import com.universidade.sistema.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public List<Aluno> listarAtivos() {
        return alunoRepository.findByAtivoTrue();
    }

    public List<Aluno> listarInativos() {
        return alunoRepository.findByAtivoFalse();
    }

    public Aluno salvar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    public void desativar(Long id) {
        Aluno aluno = buscarPorId(id);
        aluno.setAtivo(false);
        alunoRepository.save(aluno);
    }

    public void reativar(Long id) {
        Aluno aluno = buscarPorId(id);
        aluno.setAtivo(true);
        alunoRepository.save(aluno);
    }

    public List<Aluno> pesquisarAtivos(String termo) {
        return alunoRepository.findByNomeContainingIgnoreCaseAndAtivoTrue(termo);
    }

    public List<Aluno> pesquisarInativos(String termo) {
        return alunoRepository.findByNomeContainingIgnoreCaseAndAtivoFalse(termo);
    }
}

