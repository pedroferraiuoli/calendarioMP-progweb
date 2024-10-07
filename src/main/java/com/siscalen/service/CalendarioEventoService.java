package com.siscalen.service;

import com.siscalen.model.CalendarioEvento;
import com.siscalen.repository.CalendarioEventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CalendarioEventoService {

    @Autowired
    private CalendarioEventoRepository calendarioEventoRepository;

    public List<CalendarioEvento> findAll() {
        return calendarioEventoRepository.findAll();
    }

    public Optional<CalendarioEvento> findById(Long id) {
        return calendarioEventoRepository.findById(id);
    }

    public CalendarioEvento save(CalendarioEvento calendarioEvento) {
        return calendarioEventoRepository.save(calendarioEvento);
    }

    public void deleteById(Long id) {
        calendarioEventoRepository.deleteById(id);
    }

    public List<CalendarioEvento> findEventosNaoConcluidos() {
        return calendarioEventoRepository.findByConcluidoFalse();
    }
}
