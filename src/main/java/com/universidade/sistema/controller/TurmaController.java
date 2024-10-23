package com.universidade.sistema.controller;


import com.universidade.sistema.model.Turma;
import com.universidade.sistema.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    @Autowired
    private TurmaService service;

    @GetMapping("/ativas")
    public List<Turma> listarAtivas() {
        return service.listarAtivas();
    }

    @GetMapping("/inativas")
    public List<Turma> listarInativas() {
        return service.listarInativas();
    }

    @PostMapping
    public Turma criar(@RequestBody Turma turma) {
        return service.salvar(turma);
    }

    @PutMapping("/{id}")
    public Turma atualizar(@PathVariable Long id, @RequestBody Turma turmaAtualizada) {
        turmaAtualizada.setId(id);
        return service.salvar(turmaAtualizada);
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
