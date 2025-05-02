package MAGE.mage.controller;

import MAGE.mage.model.Movimentacao;
import MAGE.mage.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService service;

    @GetMapping
    public List<Movimentacao> getAllMovimentacoes() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimentacao> getMovimentacaoById(@PathVariable Integer id) {
        return service.findById(id)
                .map(movimentacao -> ResponseEntity.ok(movimentacao))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Movimentacao createMovimentacao(@RequestBody Movimentacao movimentacao) {
        return service.save(movimentacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimentacao> updateMovimentacao(@PathVariable Integer id, @RequestBody Movimentacao movimentacao) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        movimentacao.setIdMovimentacoes(id);
        Movimentacao updatedMovimentacao = service.save(movimentacao);
        return ResponseEntity.ok(updatedMovimentacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimentacao(@PathVariable Integer id) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
