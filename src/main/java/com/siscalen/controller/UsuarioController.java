package com.siscalen.controller;

import com.siscalen.model.Usuario;
import com.siscalen.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Buscar todos os usuários")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuários encontrados",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)) })
    })
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)) }),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> getUsuarioById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(usuario -> {
                    EntityModel<Usuario> resource = EntityModel.of(usuario);

                    WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getUsuarioById(id));
                    resource.add(linkToSelf.withSelfRel());

                    WebMvcLinkBuilder linkToAll = linkTo(methodOn(this.getClass()).getAllUsuarios());
                    resource.add(linkToAll.withRel("all-usuarios"));

                    return ResponseEntity.ok(resource);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar um novo usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)) })
    })
    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @Operation(summary = "Atualizar usuário por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)) }),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.findById(id)
                .map(existingUsuario -> {
                    usuario.setId(id);
                    return ResponseEntity.ok(usuarioService.save(usuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar usuário por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        if (usuarioService.findById(id).isPresent()) {
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
