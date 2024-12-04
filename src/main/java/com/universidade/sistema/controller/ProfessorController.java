package com.universidade.sistema.controller;

import org.springframework.ui.Model;
import com.universidade.sistema.model.Professor;
import com.universidade.sistema.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/ativos")
    public String listarAtivos(@RequestParam(value = "termo", required = false) String termo, Model model) {
        List<Professor> professores;

        if (termo != null && !termo.isEmpty()) {
            professores = professorService.pesquisar(termo);
        } else {
            professores = professorService.listarAtivos();  // Método para listar professores ativos
        }

        model.addAttribute("professoresAtivos", professores);
        model.addAttribute("termo", termo);
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
    public String salvarProfessor(@ModelAttribute Professor professor, Model model) {
        try {
            professorService.salvar(professor);
            return "redirect:/professores/ativos";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage()); // Adiciona mensagem de erro ao modelo
            model.addAttribute("professor", professor); // Mantém os dados já preenchidos
            return "professores/formulario_criar_professor";
        }
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
