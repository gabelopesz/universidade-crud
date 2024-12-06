package com.universidade.sistema.controller;

import com.universidade.sistema.model.*;
import com.universidade.sistema.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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
        List<Turma> turmas = turmaService.pesquisarTurmas(disciplinaId, professorId, salaId);

        model.addAttribute("turmas", turmas);
        model.addAttribute("disciplinas", disciplinaService.listarAtivas());
        model.addAttribute("professores", professorService.listarAtivos());
        model.addAttribute("salas", salaService.listarAtivas());

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
        model.addAttribute("turmaDisciplinasIds", new ArrayList<Long>());
        return "turmas/formulario_criar_turma";
    }


    @PostMapping("/salvar")
    public String salvarTurma(@ModelAttribute Turma turma,
                              @RequestParam(value = "disciplinas", required = false) List<Long> disciplinasIds,
                              @RequestParam Map<String, String> parametros) {
        if (disciplinasIds == null || disciplinasIds.isEmpty()) {
            throw new IllegalArgumentException("É necessário associar ao menos uma disciplina à turma.");
        }

        // Processar disciplinas, horários e professores
        List<TurmaDisciplina> turmaDisciplinas = disciplinasIds.stream()
                .map(disciplinaId -> {
                    Disciplina disciplina = disciplinaService.buscarPorId(disciplinaId);

                    TurmaDisciplina turmaDisciplina = new TurmaDisciplina();
                    turmaDisciplina.setTurma(turma);
                    turmaDisciplina.setDisciplina(disciplina);

                    // Validar e atribuir horários e dia da semana
                    turmaDisciplina.setHorarioInicio(parametros.get("horarioInicio_" + disciplinaId));
                    turmaDisciplina.setHorarioTermino(parametros.get("horarioTermino_" + disciplinaId));
                    turmaDisciplina.setDiaSemana(parametros.get("diaSemana_" + disciplinaId));

                    // Associar professor à disciplina
                    String professorIdStr = parametros.get("professor_" + disciplinaId);
                    if (professorIdStr != null && !professorIdStr.isEmpty()) {
                        Long professorId = Long.parseLong(professorIdStr);
                        Professor professor = professorService.buscarPorId(professorId);

                        TurmaProfessor turmaProfessor = new TurmaProfessor();
                        turmaProfessor.setProfessor(professor);
                        turmaProfessor.setTurmaDisciplina(turmaDisciplina);
                        turmaDisciplina.getTurmaProfessores().add(turmaProfessor);
                    }

                    return turmaDisciplina;
                }).collect(Collectors.toList());

        turma.setTurmaDisciplinas(turmaDisciplinas);
        turmaService.salvar(turma);

        return "redirect:/turmas/ativas";
    }

    @GetMapping("/editar/{id}")
    public String editarTurmaForm(@PathVariable("id") Long id, Model model) {
        Turma turma = turmaService.buscarPorId(id);

        Map<Long, TurmaDisciplina> turmaDisciplinasMap = turma.getTurmaDisciplinas().stream()
                .collect(Collectors.toMap(td -> td.getDisciplina().getId(), td -> td));

        model.addAttribute("turma", turma);
        model.addAttribute("disciplinas", disciplinaService.listarAtivas());
        model.addAttribute("professores", professorService.listarAtivos());
        model.addAttribute("salas", salaService.listarAtivas());
        model.addAttribute("turmaDisciplinasMap", turmaDisciplinasMap);

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

