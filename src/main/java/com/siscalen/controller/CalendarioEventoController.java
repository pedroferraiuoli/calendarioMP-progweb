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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/eventos")
public class CalendarioEventoController {

    @Autowired
    private CalendarioEventoService calendarioEventoService;

    @Operation(summary = "Buscar todos os eventos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Eventos encontrados",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CalendarioEvento.class)) })
    })
    @GetMapping
    public ResponseEntity<List<CalendarioEvento>> getAllEventos() {
        List<CalendarioEvento> eventos = calendarioEventoService.findAll();
        return ResponseEntity.ok(eventos);
    }

    @Operation(summary = "Buscar evento por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evento encontrado",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CalendarioEvento.class)) }),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CalendarioEvento>> getEventoById(
            @PathVariable Long id) {
        return calendarioEventoService.findById(id)
                .map(evento -> {
                    EntityModel<CalendarioEvento> resource = EntityModel.of(evento);
                    WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getEventoById(id));
                    resource.add(linkToSelf.withSelfRel());

                    WebMvcLinkBuilder linkToAll = linkTo(methodOn(this.getClass()).getAllEventos());
                    resource.add(linkToAll.withRel("all-events"));
                    return ResponseEntity.ok(resource);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar um novo evento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Evento criado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CalendarioEvento.class)) })
    })
    @PostMapping
    public ResponseEntity<EntityModel<CalendarioEvento>> createEvento(@RequestBody CalendarioEvento evento) {
        CalendarioEvento novoEvento = calendarioEventoService.save(evento);
        EntityModel<CalendarioEvento> resource = EntityModel.of(novoEvento);
        WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getEventoById(novoEvento.getId()));
        resource.add(linkToSelf.withSelfRel());

        return ResponseEntity.created(linkToSelf.toUri()).body(resource);
    }

    @Operation(summary = "Atualizar evento por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CalendarioEvento.class)) }),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<CalendarioEvento> updateEvento(@PathVariable Long id, @RequestBody CalendarioEvento evento) {
        return calendarioEventoService.findById(id)
                .map(existingEvento -> {
                    evento.setId(id);
                    return ResponseEntity.ok(calendarioEventoService.save(evento));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar evento por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Evento deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        if (calendarioEventoService.findById(id).isPresent()) {
            calendarioEventoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar eventos não concluídos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Eventos não concluídos encontrados",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CalendarioEvento.class)) })
    })
    @GetMapping("/nao-concluidos")
    public ResponseEntity<List<CalendarioEvento>> getEventosNaoConcluidos() {
        List<CalendarioEvento> eventos = calendarioEventoService.findEventosNaoConcluidos();
        return ResponseEntity.ok(eventos);
    }
}
