package MAGE.mage.controller;

import MAGE.mage.model.Administrador;
import MAGE.mage.model.AtribuirUsuarioRequest;
import MAGE.mage.service.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @PostMapping
    public ResponseEntity<Administrador> create(@RequestBody Administrador administrador) {
        administrador.setSenha(new BCryptPasswordEncoder().encode(administrador.getPassword())); //trecho a ser removido
        Administrador created = administradorService.create(administrador);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Administrador>> findAll() {
        List<Administrador> administradores = administradorService.findAll();
        return ResponseEntity.ok(administradores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrador> findById(@PathVariable Integer id) {
        return administradorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrador> update(@PathVariable Integer id, @RequestBody Administrador administrador) {
        return ResponseEntity.ok(administradorService.update(id, administrador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        administradorService.delete(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/atribuirUsuario")
//    public ResponseEntity<Void> atribuirUsuario(@RequestBody AtribuirUsuarioRequest request) {
//        System.out.println("ID da Máquina: " + request.getIdMaquina());
//        System.out.println("ID do Funcionário: " + request.getIdFuncionario());
//
//        administradorService.atribuirUsuario(request.getIdMaquina(), request.getIdFuncionario());
//        return ResponseEntity.ok().build();
//    }

}
