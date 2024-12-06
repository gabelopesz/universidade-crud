package com.universidade.sistema.controller;

import com.universidade.sistema.model.Aluno;
import com.universidade.sistema.service.AlunoService;
import com.universidade.sistema.service.TurmaService;
import com.universidade.sistema.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private PdfService pdfService;


    @GetMapping("/ativos")
    public String listarAlunosAtivos(@RequestParam(value = "termo", required = false) String termo, Model model) {
        if (termo != null && !termo.isEmpty()) {
            model.addAttribute("alunos", alunoService.pesquisarAtivos(termo));
        } else {
            model.addAttribute("alunos", alunoService.listarAtivos());
        }
        model.addAttribute("turmas", turmaService.listarAtivas());
        return "alunos/lista_alunos_ativos";
    }

    @GetMapping("/inativos")
    public String listarAlunosInativos(@RequestParam(value = "termo", required = false) String termo, Model model) {
        if (termo != null && !termo.isEmpty()) {
            model.addAttribute("alunosInativos", alunoService.pesquisarInativos(termo));
        } else {
            model.addAttribute("alunosInativos", alunoService.listarInativos());
        }
        return "alunos/lista_alunos_inativos";
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
        return "redirect:/alunos/ativos";
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
        return "redirect:/alunos/ativos";
    }

    @GetMapping("/reativar/{id}")
    public String reativarAluno(@PathVariable("id") Long id) {
        alunoService.reativar(id);
        return "redirect:/alunos/inativos";
    }

    @GetMapping("/exportar-horario/{id}")
    public String exportarHorarioAluno(@PathVariable("id") Long id, Model model) {
        Aluno aluno = alunoService.buscarPorId(id);

        try {
            pdfService.exportarHorarioAluno(aluno);
            model.addAttribute("mensagem", "PDF gerado com sucesso em: ~/Documentos/Faculdade/sexto-periodo/persistencia/horarios_alunos");
        } catch (IOException e) {
            model.addAttribute("mensagem", "Erro ao gerar PDF: " + e.getMessage());
        }

        return "redirect:/alunos/ativos";
    }




}

