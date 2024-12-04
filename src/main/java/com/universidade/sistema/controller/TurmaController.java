package com.universidade.sistema.controller;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String listarTurmas(@RequestParam(value = "disciplinaId", required = false) Long disciplinaId,
                               @RequestParam(value = "professorId", required = false) Long professorId,
                               @RequestParam(value = "salaId", required = false) Long salaId,
                               Model model) {
        List<Disciplina> disciplinas = disciplinaService.listarAtivas();
        List<Professor> professores = professorService.listarAtivos();
        List<Sala> salas = salaService.listarAtivas();
        List<Turma> turmas = turmaService.pesquisarTurmas(disciplinaId, professorId, salaId);

        model.addAttribute("turmas", turmas);
        model.addAttribute("disciplinas", disciplinas);
        model.addAttribute("professores", professores);
        model.addAttribute("salas", salas);

        if (turmas.isEmpty()) {
            model.addAttribute("mensagem", "Nenhum registro encontrado com esses critérios.");
        }

        return "turmas/lista_turmas_ativas";
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

    @GetMapping("/reativar/{id}")
    public String reativarTurma(@PathVariable("id") Long id) {
        turmaService.reativar(id);
        return "redirect:/turmas/inativas";
    }

    @GetMapping("/inativas")
    public String listarInativas(Model model) {
        model.addAttribute("turmasInativas", turmaService.listarInativas());
        return "turmas/lista_turmas_inativas";
    }
}

