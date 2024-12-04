package com.universidade.sistema.service;

import com.universidade.sistema.model.Sala;
import com.universidade.sistema.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalaService {

    @Autowired
    private SalaRepository repository;

    public List<Sala> listarAtivas() {
        return repository.findByAtivoTrue();
    }

    public List<Sala> listarInativas() {
        return repository.findByAtivoFalse();
    }

    public Sala salvar(Sala sala) {
        return repository.save(sala);
    }

    public void desativar(Long id) {
        Sala sala = repository.findById(id).orElseThrow(() -> new RuntimeException("Sala não encontrada"));
        sala.setAtivo(false);
        repository.save(sala);
    }

    public void reativar(Long id) {
        Sala sala = repository.findById(id).orElseThrow(() -> new RuntimeException("Sala não encontrada"));
        sala.setAtivo(true);
        repository.save(sala);
    }

    public Sala buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Sala não encontrada"));
    }

    private String removerAcentos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }

    public List<Sala> pesquisar(String termo) {
        String termoNormalizado = removerAcentos(termo.toLowerCase());
        return repository.findAll().stream()
                .filter(p -> removerAcentos(p.getNome().toLowerCase()).contains(termoNormalizado))
                .collect(Collectors.toList());
    }
}

