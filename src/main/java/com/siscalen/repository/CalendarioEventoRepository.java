package com.siscalen.repository;

import com.siscalen.model.CalendarioEvento;
import com.siscalen.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarioEventoRepository extends JpaRepository<CalendarioEvento, Long> {

    @Query("SELECT c FROM CalendarioEvento c WHERE c.titulo LIKE %:titulo%")
    List<CalendarioEvento> findByTituloContaining(String titulo);

    @Query("SELECT c FROM CalendarioEvento c WHERE c.responsavelEvento = :usuario")
    List<CalendarioEvento> findByResponsavel(Usuario usuario);

    List<CalendarioEvento> findByConcluidoFalse();
    

}
