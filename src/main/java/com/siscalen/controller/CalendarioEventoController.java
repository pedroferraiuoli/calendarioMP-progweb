package com.siscalen.controller;

import com.siscalen.model.CalendarioEvento;
import com.siscalen.service.CalendarioEventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping("/eventos")
public class CalendarioEventoController {

    @Autowired
    private CalendarioEventoService calendarioEventoService;

    @Operation(summary = "Listar todos os eventos", description = "Retorna uma lista de todos os eventos cadastrados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Eventos encontrados", 
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CalendarioEvento.class)) })
    })
    @GetMapping
    public String listarEventos(Model model) {
        List<CalendarioEvento> eventos = calendarioEventoService.findAll();
        model.addAttribute("eventos", eventos);
        return "listar";
    }

    @Operation(summary = "Exibir formulário de criação de evento", description = "Retorna um formulário para a criação de um novo evento.")
    @GetMapping("/criar")
    public String exibirFormularioCriarEvento(Model model) {
        model.addAttribute("evento", new CalendarioEvento());
        return "criar";
    }

    @Operation(summary = "Criar um novo evento", description = "Cadastra um novo evento na lista.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Evento criado com sucesso", 
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CalendarioEvento.class)) })
    })
    @PostMapping
    public String criarEvento(@ModelAttribute CalendarioEvento evento) {
        calendarioEventoService.save(evento);
        return "redirect:/eventos";
    }

    @Operation(summary = "Exibir formulário de edição de evento", description = "Retorna um formulário para a edição de um evento existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evento encontrado e formulário exibido", 
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CalendarioEvento.class)) }),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado", content = @Content)
    })
    @GetMapping("/editar/{id}")
    public String exibirFormularioEditarEvento(@PathVariable Long id, Model model) {
        CalendarioEvento evento = calendarioEventoService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento inválido: " + id));
        model.addAttribute("evento", evento);
        return "editar";
    }

    @Operation(summary = "Atualizar um evento existente", description = "Atualiza os dados de um evento existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso", 
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CalendarioEvento.class)) }),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado", content = @Content)
    })
    @PostMapping("/editar/{id}")
    public String atualizarEvento(@PathVariable Long id, @ModelAttribute CalendarioEvento evento) {
        evento.setId(id);
        calendarioEventoService.save(evento);
        return "redirect:/eventos";
    }

    @Operation(summary = "Deletar um evento por ID", description = "Remove um evento da lista pelo seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Evento deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado", content = @Content)
    })
    @GetMapping("/deletar/{id}")
    public String deletarEvento(@PathVariable Long id) {
        calendarioEventoService.deleteById(id);
        return "redirect:/eventos";
    }
}
