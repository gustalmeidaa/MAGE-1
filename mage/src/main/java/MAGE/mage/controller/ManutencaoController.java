package MAGE.mage.controller;

import MAGE.mage.model.Manutencao;
import MAGE.mage.service.ManutencaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manutencoes")
public class ManutencaoController {

    @Autowired
    private ManutencaoService manutencaoService;

    @GetMapping
    public List<Manutencao> getAllManutencoes() {
        return manutencaoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manutencao> getManutencaoById(@PathVariable Integer id) {
        return manutencaoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Manutencao createManutencao(@RequestBody Manutencao manutencao) {
        return manutencaoService.save(manutencao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Manutencao> updateManutencao(@PathVariable Integer id, @RequestBody Manutencao manutencao) {
        if (!manutencaoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        manutencao.setIdHistoricoManutencoes(id);
        return ResponseEntity.ok(manutencaoService.save(manutencao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManutencao(@PathVariable Integer id) {
        if (!manutencaoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        manutencaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
