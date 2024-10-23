package com.universidade.sistema.controller;

import org.springframework.ui.Model;
import com.universidade.sistema.model.Disciplina;
import com.universidade.sistema.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    // Exibir a lista de disciplinas ativas
    @GetMapping("/ativas")
    public String listarAtivas(Model model) {
        model.addAttribute("disciplinasAtivas", disciplinaService.listarAtivas());
        return "disciplinas/lista_disciplinas_ativas";  // Template descritivo
    }
    @GetMapping("/inativas")
    public String listarInativas(Model model) {
        model.addAttribute("disciplinasInativas", disciplinaService.listarInativas());
        return "disciplinas/lista_disciplinas_inativas";
    }

    // Reativar uma disciplina inativa
    @GetMapping("/reativar/{id}")
    public String reativarDisciplina(@PathVariable Long id) {
        disciplinaService.reativar(id);
        return "redirect:/disciplinas/inativas";  // Redireciona para a lista de inativas após reativação
    }

    // Redirecionar para a página de criar nova disciplina
    @GetMapping("/nova")
    public String criarDisciplinaForm(Model model) {
        model.addAttribute("disciplina", new Disciplina());
        return "disciplinas/formulario_criar_disciplina";  // Template descritivo
    }

    // Salvar uma nova ou editar uma disciplina
    @PostMapping("/salvar")
    public String salvarDisciplina(Disciplina disciplina) {
        disciplinaService.salvar(disciplina);
        return "redirect:/disciplinas/ativas";  // Rotas simples
    }

    // Editar uma disciplina existente
    @GetMapping("/editar/{id}")
    public String editarDisciplinaForm(@PathVariable Long id, Model model) {
        Disciplina disciplina = disciplinaService.buscarPorId(id);
        model.addAttribute("disciplina", disciplina);
        return "disciplinas/formulario_editar_disciplina";  // Template descritivo
    }

    // Desativar uma disciplina
    @GetMapping("/desativar/{id}")
    public String desativarDisciplina(@PathVariable Long id) {
        disciplinaService.desativar(id);
        return "redirect:/disciplinas/ativas";  // Rotas simples
    }
}
