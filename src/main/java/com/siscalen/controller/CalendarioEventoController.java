package com.siscalen.controller;

import com.siscalen.model.CalendarioEvento;
import com.siscalen.service.CalendarioEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class CalendarioEventoController {

    @Autowired
    private CalendarioEventoService calendarioEventoService;

    @GetMapping
    public List<CalendarioEvento> getAllEventos() {
        return calendarioEventoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarioEvento> getEventoById(@PathVariable Long id) {
        return calendarioEventoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CalendarioEvento createEvento(@RequestBody CalendarioEvento evento) {
        return calendarioEventoService.save(evento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalendarioEvento> updateEvento(@PathVariable Long id, @RequestBody CalendarioEvento evento) {
        return calendarioEventoService.findById(id)
                .map(existingEvento -> {
                    evento.setId(id);
                    return ResponseEntity.ok(calendarioEventoService.save(evento));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        if (calendarioEventoService.findById(id).isPresent()) {
            calendarioEventoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
