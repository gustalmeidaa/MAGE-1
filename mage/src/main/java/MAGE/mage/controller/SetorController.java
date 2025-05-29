package MAGE.mage.controller;

import MAGE.mage.model.Setor;
import MAGE.mage.service.SetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/setores")
public class SetorController {

    private final SetorService setorService;

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
    public ResponseEntity<Setor> createSetor(@RequestBody Setor setor) {
        Setor savedSetor = setorService.save(setor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSetor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Setor> updateSetor(@PathVariable Integer id, @RequestBody Setor setor) {
        if (!setorService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        setor.setIdSetor(id);
        Setor updatedSetor = setorService.save(setor);
        return ResponseEntity.ok(updatedSetor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSetor(@PathVariable Integer id) {
        if (!setorService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        setorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
