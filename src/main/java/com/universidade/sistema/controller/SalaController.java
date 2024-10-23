package com.universidade.sistema.controller;

import org.springframework.ui.Model;
import com.universidade.sistema.model.Sala;
import com.universidade.sistema.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @GetMapping("/ativas")
    public String listarAtivas(Model model) {
        model.addAttribute("salasAtivas", salaService.listarAtivas());
        return "salas/lista_salas_ativas";
    }

    @GetMapping("/inativas")
    public String listarInativas(Model model) {
        model.addAttribute("salasInativas", salaService.listarInativas());
        return "salas/lista_salas_inativas";
    }

    @GetMapping("/reativar/{id}")
    public String reativarSala(@PathVariable("id") Long id) {
        salaService.reativar(id);
        return "redirect:/salas/inativas";
    }

    @GetMapping("/nova")
    public String criarSalaForm(Model model) {
        model.addAttribute("sala", new Sala());
        return "salas/formulario_criar_sala";
    }

    @PostMapping("/salvar")
    public String salvarSala(@ModelAttribute Sala sala) {
        salaService.salvar(sala);
        return "redirect:/salas/ativas";
    }

    @GetMapping("/editar/{id}")
    public String editarSalaForm(@PathVariable("id") Long id, Model model) {
        Sala sala = salaService.buscarPorId(id);
        model.addAttribute("sala", sala);
        return "salas/formulario_editar_sala";
    }

    @GetMapping("/desativar/{id}")
    public String desativarSala(@PathVariable("id") Long id) {
        salaService.desativar(id);
        return "redirect:/salas/ativas";
    }
}
