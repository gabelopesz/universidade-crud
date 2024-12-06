package com.universidade.sistema.controller;

import com.universidade.sistema.model.Aluno;
import com.universidade.sistema.service.AlunoService;
import com.universidade.sistema.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private TurmaService turmaService;

    @GetMapping
    public String listarAlunos(@RequestParam(value = "turmaId", required = false) Long turmaId, Model model) {
        if (turmaId != null) {
            model.addAttribute("alunos", alunoService.listarPorTurma(turmaId));
        } else {
            model.addAttribute("alunos", alunoService.listarAtivos());
        }
        model.addAttribute("turmas", turmaService.listarAtivas());
        return "alunos/lista_alunos";
    }

    @GetMapping("/novo")
    public String criarAlunoForm(Model model) {
        model.addAttribute("aluno", new Aluno());
        model.addAttribute("turmas", turmaService.listarAtivas());
        return "alunos/formulario_criar_aluno";
    }

    @PostMapping("/salvar")
    public String salvarAluno(@ModelAttribute Aluno aluno) {
        alunoService.salvar(aluno);
        return "redirect:/alunos";
    }

    @GetMapping("/editar/{id}")
    public String editarAlunoForm(@PathVariable("id") Long id, Model model) {
        Aluno aluno = alunoService.buscarPorId(id);
        model.addAttribute("aluno", aluno);
        model.addAttribute("turmas", turmaService.listarAtivas());
        return "alunos/formulario_editar_aluno";
    }

    @GetMapping("/desativar/{id}")
    public String desativarAluno(@PathVariable("id") Long id) {
        alunoService.desativar(id);
        return "redirect:/alunos";
    }

    @GetMapping("/reativar/{id}")
    public String reativarAluno(@PathVariable("id") Long id) {
        alunoService.reativar(id);
        return "redirect:/alunos";
    }
}
