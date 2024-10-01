package com.siscalen.controller;

import com.siscalen.model.Admin;
import com.siscalen.service.AdminService;
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
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Operation(summary = "Buscar todos os admins")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Admins encontrados",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Admin.class)) })
    })
    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.findAll();
        return ResponseEntity.ok(admins);
    }

    @Operation(summary = "Buscar admin por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Admin encontrado",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Admin.class)) }),
        @ApiResponse(responseCode = "404", description = "Admin não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Admin>> getAdminById(@PathVariable Long id) {
        return adminService.findById(id)
                .map(admin -> {
                    EntityModel<Admin> resource = EntityModel.of(admin);

                    WebMvcLinkBuilder linkToSelf = linkTo(methodOn(this.getClass()).getAdminById(id));
                    resource.add(linkToSelf.withSelfRel());

                    WebMvcLinkBuilder linkToAll = linkTo(methodOn(this.getClass()).getAllAdmins());
                    resource.add(linkToAll.withRel("all-admins"));

                    return ResponseEntity.ok(resource);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar um novo admin")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Admin criado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Admin.class)) })
    })
    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.save(admin);
    }

    @Operation(summary = "Atualizar admin por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Admin atualizado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Admin.class)) }),
        @ApiResponse(responseCode = "404", description = "Admin não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        return adminService.findById(id)
                .map(existingAdmin -> {
                    admin.setId(id);
                    return ResponseEntity.ok(adminService.save(admin));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar admin por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Admin deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Admin não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        if (adminService.findById(id).isPresent()) {
            adminService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
