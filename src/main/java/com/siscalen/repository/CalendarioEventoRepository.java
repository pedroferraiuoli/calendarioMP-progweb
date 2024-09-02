package com.siscalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.siscalen.model.CalendarioEvento;

@Repository
public interface CalendarioEventoRepository extends JpaRepository<CalendarioEvento, Long> {
}
