package com.siscalen.model;

import javax.persistence.Entity;

@Entity
public class Admin extends Usuario {
    
    private String nivelAcesso;

    // Getters e Setters
    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
}

