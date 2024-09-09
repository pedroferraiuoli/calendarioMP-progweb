package com.siscalen.model;

import jakarta.persistence.Entity;

@Entity
public class Admin extends Usuario {

    private String nivelAcesso;


    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
}
