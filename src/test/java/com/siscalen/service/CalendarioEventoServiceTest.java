package com.siscalen.service;

import com.siscalen.model.CalendarioEvento;
import com.siscalen.repository.CalendarioEventoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalendarioEventoServiceTest {

    @Mock
    private CalendarioEventoRepository calendarioEventoRepository;

    @InjectMocks
    private CalendarioEventoService calendarioEventoService;

    private CalendarioEvento evento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        evento = new CalendarioEvento();
        evento.setId(1L);
        evento.setTitulo("Evento Teste");
        evento.setDataLimite(LocalDate.now());
        evento.setConcluido(false);
    }

    @Test
    void testFindAll() {
        when(calendarioEventoRepository.findAll()).thenReturn(Arrays.asList(evento));

        List<CalendarioEvento> eventos = calendarioEventoService.findAll();

        assertEquals(1, eventos.size());
        assertEquals("Evento Teste", eventos.get(0).getTitulo());
        verify(calendarioEventoRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(calendarioEventoRepository.findById(1L)).thenReturn(Optional.of(evento));

        Optional<CalendarioEvento> foundEvento = calendarioEventoService.findById(1L);

        assertTrue(foundEvento.isPresent());
        assertEquals("Evento Teste", foundEvento.get().getTitulo());
        verify(calendarioEventoRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        when(calendarioEventoRepository.save(evento)).thenReturn(evento);

        CalendarioEvento savedEvento = calendarioEventoService.save(evento);

        assertNotNull(savedEvento);
        assertEquals("Evento Teste", savedEvento.getTitulo());
        verify(calendarioEventoRepository, times(1)).save(evento);
    }

    @Test
    void testDeleteById() {
        doNothing().when(calendarioEventoRepository).deleteById(1L);

        calendarioEventoService.deleteById(1L);

        verify(calendarioEventoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindAllFail() {
        when(calendarioEventoRepository.findAll()).thenReturn(Arrays.asList());

        List<CalendarioEvento> eventos = calendarioEventoService.findAll();

        assertFalse(eventos.isEmpty(), "A lista de eventos não deve estar vazia");

        verify(calendarioEventoRepository, times(1)).findAll();
    }
}
