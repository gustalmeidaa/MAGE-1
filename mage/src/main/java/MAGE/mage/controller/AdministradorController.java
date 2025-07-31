package MAGE.mage.controller;

import MAGE.mage.dto.AdministradorDto;
import MAGE.mage.model.Administrador;
import MAGE.mage.service.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @PostMapping
    public ResponseEntity<Administrador> create(@RequestBody AdministradorDto administradorDto){
        Administrador administrador = administradorService.create(administradorDto);
        if (administrador == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(administrador);
    }


    @GetMapping
    public ResponseEntity<List<Administrador>> findAll() {
        List<Administrador> administradores = administradorService.findAll();
        return ResponseEntity.ok(administradores);
    }

    @GetMapping("/{login}")
    public ResponseEntity<Administrador> findById(@PathVariable String login) {
        return administradorService.findById(login)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Administrador> update(@RequestBody Administrador administrador) {
        Administrador administrador1 = administradorService.update(administrador);
        if (administrador1 == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        return ResponseEntity.ok(administrador1);
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<Void> delete(@PathVariable String login) {
        administradorService.delete(login);
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
