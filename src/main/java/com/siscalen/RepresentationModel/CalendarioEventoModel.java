package com.siscalen.RepresentationModel;

import com.siscalen.model.CalendarioEvento;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

public class CalendarioEventoModel extends RepresentationModel<CalendarioEventoModel> {

    private Long id;
    private String titulo;
    private String descricao;
    private LocalDate dataLimite;
    private Boolean concluido;
    private LocalDate dataConclusao;
    private String observacao;
    private String recorrencia;

    public CalendarioEventoModel(CalendarioEvento calendarioEvento) {
        this.id = calendarioEvento.getId();
        this.titulo = calendarioEvento.getTitulo();
        this.descricao = calendarioEvento.getDescricao();
        this.dataLimite = calendarioEvento.getDataLimite();
        this.concluido = calendarioEvento.getConcluido();
        this.dataConclusao = calendarioEvento.getDataConclusao();
        this.observacao = calendarioEvento.getObservacao();
        this.recorrencia = calendarioEvento.getRecorrencia();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }

    public Boolean getConcluido() {
        return concluido;
    }

    public void setConcluido(Boolean concluido) {
        this.concluido = concluido;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getRecorrencia() {
        return recorrencia;
    }

    public void setRecorrencia(String recorrencia) {
        this.recorrencia = recorrencia;
    }
}
