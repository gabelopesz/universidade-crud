package com.universidade.sistema.controller;

import com.universidade.sistema.model.*;
import com.universidade.sistema.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    public String salvarTurma(@ModelAttribute Turma turma,
                              @RequestParam("disciplinas") List<Long> disciplinasIds,
                              @RequestParam(value = "professores", required = false) List<Long> professoresIds,
                              @RequestParam Map<String, String> horarios) {
        // Valida se há disciplinas associadas
        if (disciplinasIds == null || disciplinasIds.isEmpty()) {
            throw new IllegalArgumentException("É necessário associar ao menos uma disciplina à turma.");
        }

        // Criação das associações de TurmaDisciplina com horários e dia da semana
        List<TurmaDisciplina> turmaDisciplinas = disciplinasIds.stream()
                .map(disciplinaId -> {
                    Disciplina disciplina = disciplinaService.buscarPorId(disciplinaId);
                    if (disciplina == null) {
                        throw new IllegalArgumentException("Disciplina com ID " + disciplinaId + " não encontrada.");
                    }

                    TurmaDisciplina turmaDisciplina = new TurmaDisciplina();
                    turmaDisciplina.setTurma(turma);
                    turmaDisciplina.setDisciplina(disciplina);
                    turmaDisciplina.setHorarioInicio(horarios.get("horarioInicio_" + disciplinaId));
                    turmaDisciplina.setHorarioTermino(horarios.get("horarioTermino_" + disciplinaId));
                    turmaDisciplina.setDiaSemana(horarios.get("diaSemana_" + disciplinaId));
                    return turmaDisciplina;
                }).collect(Collectors.toList());

        turma.setTurmaDisciplinas(turmaDisciplinas);

        // Criação das associações de TurmaProfessor
        if (professoresIds != null && !professoresIds.isEmpty()) {
            List<TurmaProfessor> turmaProfessores = professoresIds.stream()
                    .map(professorId -> {
                        Professor professor = professorService.buscarPorId(professorId);
                        if (professor == null) {
                            throw new IllegalArgumentException("Professor com ID " + professorId + " não encontrado.");
                        }

                        TurmaProfessor turmaProfessor = new TurmaProfessor();
                        turmaProfessor.setTurma(turma);
                        turmaProfessor.setProfessor(professor);

                        // Atribui a primeira disciplina selecionada como padrão
                        turmaProfessor.setDisciplina(turmaDisciplinas.get(0).getDisciplina());

                        return turmaProfessor;
                    }).collect(Collectors.toList());

            turma.setTurmaProfessores(turmaProfessores);
        }

        turmaService.salvar(turma);
        return "redirect:/turmas/ativas";
    }

    @GetMapping("/editar/{id}")
    public String editarTurmaForm(@PathVariable("id") Long id, Model model) {
        Turma turma = turmaService.buscarPorId(id);

        // Mapeia as disciplinas e professores associados
        Map<Long, TurmaDisciplina> turmaDisciplinasMap = turma.getTurmaDisciplinas().stream()
                .collect(Collectors.toMap(td -> td.getDisciplina().getId(), td -> td));

        Map<Long, TurmaProfessor> turmaProfessoresMap = turma.getTurmaProfessores().stream()
                .collect(Collectors.toMap(tp -> tp.getProfessor().getId(), tp -> tp));

        model.addAttribute("turma", turma);
        model.addAttribute("disciplinas", disciplinaService.listarAtivas());
        model.addAttribute("professores", professorService.listarAtivos());
        model.addAttribute("salas", salaService.listarAtivas());
        model.addAttribute("turmaDisciplinasMap", turmaDisciplinasMap);
        model.addAttribute("turmaProfessoresMap", turmaProfessoresMap);

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
