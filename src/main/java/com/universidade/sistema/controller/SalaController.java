package com.universidade.sistema.controller;

import com.universidade.sistema.model.Sala;
import com.universidade.sistema.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaService service;

    @GetMapping("/ativas")
    public List<Sala> listarAtivas() {
        return service.listarAtivas();
    }

    @GetMapping("/inativas")
    public List<Sala> listarInativas() {
        return service.listarInativas();
    }

    @PostMapping
    public Sala criar(@RequestBody Sala sala) {
        return service.salvar(sala);
    }

    @PutMapping("/{id}")
    public Sala atualizar(@PathVariable Long id, @RequestBody Sala salaAtualizada) {
        salaAtualizada.setId(id);
        return service.salvar(salaAtualizada);
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
