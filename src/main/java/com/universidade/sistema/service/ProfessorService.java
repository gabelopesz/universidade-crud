package com.universidade.sistema.service;

import com.universidade.sistema.model.Professor;
import com.universidade.sistema.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import java.util.regex.Pattern;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository repository;

    private static final String REGEX_CPF = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";

    public List<Professor> listarAtivos() {
        return repository.findByAtivoTrue();
    }

    public List<Professor> listarInativos() {
        return repository.findByAtivoFalse();
    }

    public Professor salvar(Professor professor) {
        validarCpf(professor.getCpf());
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

    private String removerAcentos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }

    public List<Professor> pesquisar(String termo) {
        String termoNormalizado = removerAcentos(termo.toLowerCase());
        return repository.findAll().stream()
                .filter(p -> removerAcentos(p.getNome().toLowerCase()).contains(termoNormalizado))
                .collect(Collectors.toList());
    }

    private void validarCpf(String cpf) {
        if (cpf == null || !Pattern.matches(REGEX_CPF, cpf)) {
            throw new IllegalArgumentException("CPF inválido. O formato correto é 000.000.000-00.");
        }
    }
}

