package com.universidade.sistema.controller;

import com.universidade.sistema.model.Aluno;
import com.universidade.sistema.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    // Endpoint para buscar todos os alunos
    @GetMapping
    public List<Aluno> getAllAlunos() {
        return alunoService.findAll();
    }

    // Endpoint para buscar um aluno pelo ID
    @GetMapping("/{id}")
    public Aluno getAlunoById(@PathVariable Long id) {
        return alunoService.findById(id);
    }

    // Endpoint para criar ou atualizar um aluno
    @PostMapping
    public Aluno createOrUpdateAluno(@RequestBody Aluno aluno) {
        return alunoService.save(aluno);
    }

    // Endpoint para excluir um aluno pelo ID
    @DeleteMapping("/{id}")
    public void deleteAluno(@PathVariable Long id) {
        alunoService.deleteById(id);
    }
}
