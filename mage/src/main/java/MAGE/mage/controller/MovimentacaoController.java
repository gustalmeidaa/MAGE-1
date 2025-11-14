package MAGE.mage.controller;

import MAGE.mage.dto.MovimentacaoDTO;
import MAGE.mage.model.Maquina;
import MAGE.mage.model.Movimentacao;
import MAGE.mage.repository.MaquinaRepository;
import MAGE.mage.security.TokenService;
import MAGE.mage.service.MovimentacaoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private TokenService tokenService;

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
    public ResponseEntity<Movimentacao> createMovimentacao(@RequestBody MovimentacaoDTO movimentacaoDTO, HttpServletRequest request) {
        String loginUsuario = tokenService.getCurrentUserLogin(request);
        Movimentacao movimentacao = new Movimentacao();

        Optional<Maquina> maquinaOptional = maquinaRepository.findById(movimentacaoDTO.idMaquinaMovimentada());
        if (maquinaOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        movimentacao.setMaquinaMovimentada(maquinaOptional.get());
        movimentacao.setTipo(movimentacaoDTO.tipo());
        movimentacao.setOrigem(movimentacaoDTO.origem());
        movimentacao.setDestino(movimentacaoDTO.destino());
        movimentacao.setData(movimentacaoDTO.data());

        Movimentacao movimentacaoSalva = service.save(movimentacao, loginUsuario); // Passar loginUsuario aqui
        return ResponseEntity.ok(movimentacaoSalva);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Movimentacao> updateMovimentacao(@PathVariable Integer id, @RequestBody MovimentacaoDTO movimentacaoDTO, HttpServletRequest request) {
        String loginUsuario = tokenService.getCurrentUserLogin(request);
        if (service.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Movimentacao movimentacao = service.dtoParaMovimentacao(movimentacaoDTO);

        movimentacao.setIdMovimentacoes(id);
        Movimentacao updatedMovimentacao = service.update(movimentacao, loginUsuario);
        return ResponseEntity.ok(updatedMovimentacao);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimentacao(@PathVariable Integer id, HttpServletRequest request) {
        String loginUsuario = tokenService.getCurrentUserLogin(request);
        if (service.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id, loginUsuario);
        return ResponseEntity.noContent().build();
    }

}
