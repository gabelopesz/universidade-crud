package com.universidade.sistema.controller;

import org.springframework.ui.Model;
import com.universidade.sistema.model.Professor;
import com.universidade.sistema.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/professores")
public class ProfessorController {
    @Autowired
    private ProfessorService service;

    @GetMapping("/ativos")
    public List<Professor> listarAtivos() {
        return service.listarAtivos();
    }

    @GetMapping("/inativos")
    public List<Professor> listarInativos() {
        return service.listarInativos();
    }

    @PostMapping
    public Professor criar(@RequestBody Professor professor) {
        return service.salvar(professor);
    }

    @PutMapping("/{id}")
    public Professor atualizar(@PathVariable Long id, @RequestBody Professor professorAtualizado) {
        professorAtualizado.setId(id);
        return service.salvar(professorAtualizado);
    }

    @DeleteMapping("/{id}")
    public void desativar(@PathVariable Long id) {
        service.desativar(id);
    }

    @PutMapping("/reativar/{id}")
    public void reativar(@PathVariable Long id) {
        service.reativar(id);
    }
}

