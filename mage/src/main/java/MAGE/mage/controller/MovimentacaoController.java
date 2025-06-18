package MAGE.mage.controller;

import MAGE.mage.dto.MovimentacaoDTO;
import MAGE.mage.model.Maquina;
import MAGE.mage.model.Movimentacao;
import MAGE.mage.repository.MaquinaRepository;
import MAGE.mage.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService service;
    @Autowired
    private MaquinaRepository maquinaRepository;

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
    public ResponseEntity<Movimentacao> createMovimentacao(@RequestBody MovimentacaoDTO movimentacaoDTO) {
        Movimentacao movimentacao = new Movimentacao();

        Optional<Maquina> maquinaOptional = maquinaRepository.findById(movimentacaoDTO.idMaquinaMovimentada());
        if (maquinaOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        movimentacao.setMaquinaMovimentada(maquinaOptional.get());

        movimentacao.setTipo(movimentacaoDTO.tipo());
        movimentacao.setOrigem(movimentacaoDTO.origem());
        movimentacao.setDestino(movimentacaoDTO.destino());
        movimentacao.setData(movimentacaoDTO.data()); // Adicionado o setData

        Movimentacao movimentacaoSalva = service.save(movimentacao);
        return ResponseEntity.ok(movimentacaoSalva); // Retornar a movimentacao salva
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
