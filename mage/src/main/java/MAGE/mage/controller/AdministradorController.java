package MAGE.mage.controller;

import MAGE.mage.dto.AdministradorDto;
import MAGE.mage.model.Administrador;
import MAGE.mage.model.AtribuirUsuarioRequest;
import MAGE.mage.security.TokenService;
import MAGE.mage.service.AdministradorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AdministradorService administradorService;


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

    @PostMapping
    public ResponseEntity<Administrador> create(@RequestBody AdministradorDto administradorDto, HttpServletRequest request) {
        String loginUsuario = tokenService.getCurrentUserLogin(request);
        Administrador administrador = administradorService.create(administradorDto, loginUsuario);
        if (administrador == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(administrador);
    }

    @PutMapping
    public ResponseEntity<Administrador> update(@RequestBody Administrador administrador, HttpServletRequest request) {
        String loginUsuario = tokenService.getCurrentUserLogin(request);
        System.out.println("\n Quase lá. o service será executado \n");
        Administrador administrador1 = administradorService.update(administrador, loginUsuario);
        if (administrador1 == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        return ResponseEntity.ok(administrador1);
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<Void> delete(@PathVariable String login, HttpServletRequest request) {
        String loginUsuario = tokenService.getCurrentUserLogin(request);
        administradorService.delete(login, loginUsuario);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/atribuirUsuario")
    public ResponseEntity<Void> atribuirUsuario(@RequestBody AtribuirUsuarioRequest atribuirUsuarioRequest, HttpServletRequest request) {
        String loginUsuario = tokenService.getCurrentUserLogin(request);
        administradorService.atribuirUsuario(atribuirUsuarioRequest.getIdMaquina(), atribuirUsuarioRequest.getIdFuncionario(), loginUsuario);
        return ResponseEntity.ok().build();
    }

}
