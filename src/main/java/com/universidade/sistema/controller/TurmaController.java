package com.universidade.sistema.controller;

import org.springframework.ui.Model;
import com.universidade.sistema.model.Turma;
import com.universidade.sistema.model.Disciplina;
import com.universidade.sistema.model.Professor;
import com.universidade.sistema.model.Sala;
import com.universidade.sistema.service.TurmaService;
import com.universidade.sistema.service.DisciplinaService;
import com.universidade.sistema.service.ProfessorService;
import com.universidade.sistema.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/turmas")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private SalaService salaService;

    @GetMapping("/ativas")
    public String listarAtivas(Model model) {
        model.addAttribute("turmasAtivas", turmaService.listarAtivas());
        return "turmas/lista_turmas_ativas";
    }

    @GetMapping("/inativas")
    public String listarInativas(Model model) {
        model.addAttribute("turmasInativas", turmaService.listarInativas());
        return "turmas/lista_turmas_inativas";
    }

    @GetMapping("/reativar/{id}")
    public String reativarTurma(@PathVariable("id") Long id) {
        turmaService.reativar(id);
        return "redirect:/turmas/inativas";
    }

    @GetMapping("/nova")
    public String criarTurmaForm(Model model) {
        model.addAttribute("turma", new Turma());
        model.addAttribute("disciplinas", disciplinaService.listarAtivas());
        model.addAttribute("professores", professorService.listarAtivos());
        model.addAttribute("salas", salaService.listarAtivas());
        return "turmas/formulario_criar_turma";
    }

    @PostMapping("/salvar")
    public String salvarTurma(@ModelAttribute Turma turma) {
        // Buscar as entidades relacionadas pelo ID antes de salvar
        Disciplina disciplina = disciplinaService.buscarPorId(turma.getDisciplina().getId());
        Professor professor = professorService.buscarPorId(turma.getProfessor().getId());
        Sala sala = salaService.buscarPorId(turma.getSala().getId());

        // Associar as entidades relacionadas à turma
        turma.setDisciplina(disciplina);
        turma.setProfessor(professor);
        turma.setSala(sala);

        turmaService.salvar(turma);
        return "redirect:/turmas/ativas";
    }

    @GetMapping("/editar/{id}")
    public String editarTurmaForm(@PathVariable("id") Long id, Model model) {
        Turma turma = turmaService.buscarPorId(id);
        model.addAttribute("turma", turma);
        model.addAttribute("disciplinas", disciplinaService.listarAtivas());
        model.addAttribute("professores", professorService.listarAtivos());
        model.addAttribute("salas", salaService.listarAtivas());
        return "turmas/formulario_editar_turma";
    }

    @GetMapping("/desativar/{id}")
    public String desativarTurma(@PathVariable("id") Long id) {
        turmaService.desativar(id);
        return "redirect:/turmas/ativas";
    }
}
