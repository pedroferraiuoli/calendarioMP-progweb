package com.siscalen.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Admin extends Usuario {

    @NotBlank(message = "O nível de acesso não pode ser vazio")
    private String nivelAcesso;

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
}
