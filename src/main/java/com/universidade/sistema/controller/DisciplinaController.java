package com.universidade.sistema.controller;

import org.springframework.ui.Model;
import com.universidade.sistema.model.Disciplina;
import com.universidade.sistema.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @GetMapping("/ativas")
    public String listarAtivas(@RequestParam(value = "termo", required = false) String termo, Model model) {
        List<Disciplina> disciplinas;

        if (termo != null && !termo.isEmpty()) {
            disciplinas = disciplinaService.pesquisar(termo);
        } else {
            disciplinas = disciplinaService.listarAtivas();
        }

        model.addAttribute("disciplinasAtivas", disciplinas);
        model.addAttribute("termo", termo); // Manter o termo na busca
        return "disciplinas/lista_disciplinas_ativas";
    }

    @GetMapping("/inativas")
    public String listarInativas(Model model) {
        model.addAttribute("disciplinasInativas", disciplinaService.listarInativas());
        return "disciplinas/lista_disciplinas_inativas";
    }

    @GetMapping("/reativar/{id}")
    public String reativarDisciplina(@PathVariable("id") Long id) {
        disciplinaService.reativar(id);
        return "redirect:/disciplinas/inativas";
    }

    @GetMapping("/nova")
    public String criarDisciplinaForm(Model model) {
        model.addAttribute("disciplina", new Disciplina());
        return "disciplinas/formulario_criar_disciplina";
    }

    @PostMapping("/salvar")
    public String salvarDisciplina(Disciplina disciplina, Model model) {
        try {
            validarCodigo(disciplina.getCodigo());
            disciplinaService.salvar(disciplina);
            return "redirect:/disciplinas/ativas";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("disciplina", disciplina);
            return "disciplinas/formulario_criar_disciplina";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarDisciplinaForm(@PathVariable("id") Long id, Model model) {
        Disciplina disciplina = disciplinaService.buscarPorId(id);
        model.addAttribute("disciplina", disciplina);
        return "disciplinas/formulario_editar_disciplina"; // Template descritivo
    }

    @GetMapping("/desativar/{id}")
    public String desativarDisciplina(@PathVariable("id") Long id) {
        disciplinaService.desativar(id);
        return "redirect:/disciplinas/ativas"; // Rotas simples
    }

    /**
     * Valida o código da disciplina.
     * @param codigo Código da disciplina a ser validado.
     */
    private void validarCodigo(String codigo) {
        if (codigo == null || codigo.length() != 6) {
            throw new IllegalArgumentException("O código da disciplina deve ter exatamente 6 caracteres.");
        }
    }
}
