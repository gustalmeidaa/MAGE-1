package MAGE.mage.controller;

import MAGE.mage.model.ManutencaoAgendada;
import MAGE.mage.service.ManutencaoAgendadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manutencoes-agendadas")
public class ManutencaoAgendadaController {

    @Autowired
    private ManutencaoAgendadaService service;

    @GetMapping
    public List<ManutencaoAgendada> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManutencaoAgendada> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ManutencaoAgendada create(@RequestBody ManutencaoAgendada manutencaoAgendada) {

        return service.save(manutencaoAgendada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManutencaoAgendada> update(@PathVariable Integer id, @RequestBody ManutencaoAgendada manutencaoAgendada) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        manutencaoAgendada.setIdManutencaoAgendada(id);
        return ResponseEntity.ok(service.save(manutencaoAgendada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
