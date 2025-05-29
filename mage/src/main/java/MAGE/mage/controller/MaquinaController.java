package MAGE.mage.controller;

import MAGE.mage.dto.MaquinaDTO;
import MAGE.mage.model.Funcionario;
import MAGE.mage.model.Maquina;
import MAGE.mage.repository.FuncionarioRepository;
import MAGE.mage.service.MaquinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/maquinas")
public class MaquinaController {

    @Autowired
    private MaquinaService maquinaService;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @PostMapping
    public ResponseEntity<Maquina> criarMaquina(@RequestBody MaquinaDTO maquinaDTO) {
        Maquina maquina = new Maquina();
        maquina.setCodPatrimonial(maquinaDTO.codPatrimonial());
        maquina.setNumSerie(maquinaDTO.numSerie());
        maquina.setValor(maquinaDTO.valor());
        maquina.setLocalizacao(maquinaDTO.localizacao());
        maquina.setStatus(maquinaDTO.status());

        if (maquinaDTO.idResponsavel() != null) {
            Optional<Funcionario> responsavelOptional = funcionarioRepository.findById(maquinaDTO.idResponsavel());
            if (responsavelOptional.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
//            maquina.setIdResponsavel(maquinaDTO.idResponsavel());
        }

        Maquina maquinaSalva = maquinaService.cadastrarMaquina(maquina);

        return ResponseEntity.ok(maquinaSalva);
    }

    @GetMapping
    public ResponseEntity<List<Maquina>> listarMaquinas() {
        List<Maquina> maquinas = maquinaService.listarMaquinas();
        return ResponseEntity.ok(maquinas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maquina> buscarMaquina(@PathVariable Integer id) {
        Optional<Maquina> maquinaOptional = maquinaService.buscarMaquina(id);
        if (maquinaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Maquina maquina = maquinaOptional.get();
        return ResponseEntity.ok(maquina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Maquina> atualizarMaquina(@PathVariable Integer id, @RequestBody MaquinaDTO maquinaDTO) {
        Optional<Maquina> maquinaOptional = maquinaService.buscarMaquina(id);
        if (maquinaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Maquina maquina = maquinaOptional.get();

        // Alteração de responsavel
        if (maquinaDTO.idResponsavel() != null) {
            Optional<Funcionario> responsavelOptional = funcionarioRepository.findById(maquinaDTO.idResponsavel());
            if(responsavelOptional.isPresent()){
//                maquina.setIdResponsavel(maquinaDTO.idResponsavel());
            }else {
                return ResponseEntity.badRequest().build();
            }
        }else{
            maquina.setIdResponsavel(null);
        }

        maquina.setCodPatrimonial(maquinaDTO.codPatrimonial());
        maquina.setNumSerie(maquinaDTO.numSerie());
        maquina.setValor(maquinaDTO.valor());
        maquina.setLocalizacao(maquinaDTO.localizacao());
        maquina.setStatus(maquinaDTO.status());

        Maquina maquinaAtualizada = maquinaService.atualizarMaquina(maquina);

        return ResponseEntity.ok(maquinaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirMaquina(@PathVariable Integer id) {
        maquinaService.excluirMaquina(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    public ResponseEntity<MaquinaService.StatusCounts> getStatusCounts() {
        MaquinaService.StatusCounts statusCounts = maquinaService.getStatusCounts();
        return ResponseEntity.ok(statusCounts);
    }

    @GetMapping("/buscar/cod-patrimonial/{codPatrimonial}")
    public ResponseEntity<List<Maquina>> buscarPorCodPatrimonial(@PathVariable String codPatrimonial) {
        List<Maquina> maquinas = maquinaService.buscarPorCodPatrimonial(codPatrimonial);
        return ResponseEntity.ok(maquinas);
    }

    // Endpoint para buscar máquinas pelo número de série
    @GetMapping("/buscar/num-serie/{numSerie}")
    public ResponseEntity<List<Maquina>> buscarPorNumSerie(@PathVariable String numSerie) {
        List<Maquina> maquinas = maquinaService.buscarPorNumSerie(numSerie);
        return ResponseEntity.ok(maquinas);
    }

}
