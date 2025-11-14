package MAGE.mage.controller;

import MAGE.mage.dto.FuncionarioDTO;
import MAGE.mage.model.Funcionario;
import MAGE.mage.security.TokenService;
import MAGE.mage.service.FuncionarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    @Lazy
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<Funcionario> createFuncionario(@RequestBody FuncionarioDTO funcionarioDTO, HttpServletRequest request) {
        String loginUsuario = tokenService.getCurrentUserLogin(request);
        Funcionario createdFuncionario = funcionarioService.createFuncionario(funcionarioDTO, loginUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFuncionario);
    }

    @GetMapping
    public List<Funcionario> getAllFuncionarios() {
        return funcionarioService.getAllFuncionarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getFuncionarioById(@PathVariable Integer id) {
        Optional<Funcionario> funcionario = funcionarioService.getFuncionarioById(id);
        if (funcionario.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(funcionario.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> updateFuncionario(@PathVariable Integer id, @RequestBody FuncionarioDTO funcionarioDTO, HttpServletRequest request) {
        String loginUsuario = tokenService.getCurrentUserLogin(request);
        try {
            Funcionario updatedFuncionario = funcionarioService.updateFuncionario(id, funcionarioDTO, loginUsuario);
            return ResponseEntity.ok(updatedFuncionario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Integer id, HttpServletRequest request) {
        String loginUsuario = tokenService.getCurrentUserLogin(request);
        try {
            System.out.println("\n O service será executado \n");
            funcionarioService.deleteFuncionario(id, loginUsuario);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
