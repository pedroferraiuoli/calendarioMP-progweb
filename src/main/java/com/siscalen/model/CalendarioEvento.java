package com.siscalen.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "calendario_evento")
public class CalendarioEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    @NotEmpty(message = "Título não pode ser vazio")
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_limite", nullable = false)
    @NotNull(message = "Data limite não pode ser nula")
    private LocalDate dataLimite;

    @ManyToOne
    @JoinColumn(name = "responsavel_evento", nullable = true)
    private Usuario responsavelEvento;

    @Column(nullable = false)
    private Boolean concluido = false;

    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    @Column
    @Size(max = 100, message = "Recorrência não pode ter mais de 100 caracteres")
    private String recorrencia;

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



    public Usuario getResponsavelEvento() {
        return responsavelEvento;
    }



    public void setResponsavelEvento(Usuario responsavelEvento) {
        this.responsavelEvento = responsavelEvento;
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



    @Override
    public String toString() {
        return this.titulo;
    }
}
