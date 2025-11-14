package MAGE.mage.controller;

import MAGE.mage.model.Setor;
import MAGE.mage.security.SecurityFilter;
import MAGE.mage.security.TokenService;
import MAGE.mage.service.SetorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/setores")
public class SetorController {

    @Autowired
    private final SetorService setorService;
    @Autowired
    private TokenService tokenService;

    @Autowired
    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @GetMapping
    public List<Setor> getAllSetores() {
        return setorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Setor> getSetorById(@PathVariable Integer id) {
        Optional<Setor> setor = setorService.findById(id);
        return setor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Setor> createSetor(@RequestBody Setor setor, HttpServletRequest request) {
        String loginUsuario = tokenService.getCurrentUserLogin(request);
        Setor savedSetor = setorService.save(setor, loginUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSetor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Setor> updateSetor(@PathVariable Integer id, @RequestBody Setor setor, HttpServletRequest request) {
        if (setorService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        setor.setIdSetor(id);
        String loginUsuario = tokenService.getCurrentUserLogin(request);
        Setor updatedSetor = setorService.update(setor, loginUsuario, id);
        return ResponseEntity.ok(updatedSetor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSetor(@PathVariable Integer id, HttpServletRequest request) {
        if (setorService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String loginUsuario = tokenService.getCurrentUserLogin(request);
        setorService.deleteById(id, loginUsuario);
        return ResponseEntity.noContent().build();
    }
}
