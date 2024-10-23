package com.universidade.sistema.controller;

import org.springframework.ui.Model;
import com.universidade.sistema.model.Professor;
import com.universidade.sistema.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/ativos")
    public String listarAtivos(Model model) {
        model.addAttribute("professoresAtivos", professorService.listarAtivos());
        return "professores/lista_professores_ativos";
    }

    @GetMapping("/inativos")
    public String listarInativos(Model model) {
        model.addAttribute("professoresInativos", professorService.listarInativos());
        return "professores/lista_professores_inativos";
    }

    @GetMapping("/reativar/{id}")
    public String reativarProfessor(@PathVariable("id") Long id) {
        professorService.reativar(id);
        return "redirect:/professores/inativos";
    }

    @GetMapping("/novo")
    public String criarProfessorForm(Model model) {
        model.addAttribute("professor", new Professor());
        return "professores/formulario_criar_professor";
    }

    @PostMapping("/salvar")
    public String salvarProfessor(@ModelAttribute Professor professor) {
        professorService.salvar(professor);
        return "redirect:/professores/ativos";  // Corrigido para a rota correta
    }

    @GetMapping("/editar/{id}")
    public String editarProfessorForm(@PathVariable("id") Long id, Model model) {
        Professor professor = professorService.buscarPorId(id);
        model.addAttribute("professor", professor);
        return "professores/formulario_editar_professor";
    }

    @GetMapping("/desativar/{id}")
    public String desativarProfessor(@PathVariable("id") Long id) {
        professorService.desativar(id);
        return "redirect:/professores/ativos";  // Corrigido para a rota correta
    }

}
