// package com.siscalen.RepresentationModel;

import com.siscalen.model.Usuario;
import org.springframework.hateoas.RepresentationModel;

public class UsuarioModel extends RepresentationModel<UsuarioModel> {

    private Long id;
    private String nome;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
