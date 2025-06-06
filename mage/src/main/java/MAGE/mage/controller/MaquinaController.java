package MAGE.mage.controller;

import MAGE.mage.dto.MaquinaDTO;
import MAGE.mage.model.Funcionario;
import MAGE.mage.model.Maquina;
import MAGE.mage.repository.FuncionarioRepository;
import MAGE.mage.service.MaquinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/maquinas")
public class MaquinaController {

    @Autowired
    private MaquinaService maquinaService;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @PostMapping
    public ResponseEntity<MaquinaDTO> criarMaquina(@RequestBody MaquinaDTO maquinaDTO) {
        Maquina maquina = maquinaService.dtoParaMaquina(maquinaDTO);

        Maquina maquinaSalva = maquinaService.cadastrarMaquina(maquina);
        MaquinaDTO maquinaSalvaDTO = maquinaService.maquinaParaDTO(maquinaSalva);

        return ResponseEntity.ok(maquinaSalvaDTO);
    }

    @GetMapping
    public ResponseEntity<List<MaquinaDTO>> listarMaquinas() {
        List<Maquina> maquinas = maquinaService.listarMaquinas();
        List<MaquinaDTO> maquinasDTO = new ArrayList<>();
        for (Maquina maquina : maquinas){
            maquinasDTO.add(maquinaService.maquinaParaDTO(maquina));
        }
        return ResponseEntity.ok(maquinasDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaquinaDTO> buscarMaquina(@PathVariable Integer id) {
        Optional<Maquina> maquinaOptional = maquinaService.buscarMaquina(id);
        if (maquinaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Maquina maquina = maquinaOptional.get();
        MaquinaDTO maquinaDTO = maquinaService.maquinaParaDTO(maquina);
        return ResponseEntity.ok(maquinaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaquinaDTO> atualizarMaquina(@PathVariable Integer id, @RequestBody MaquinaDTO maquinaDTO) {
        Optional<Maquina> maquinaOptional = maquinaService.buscarMaquina(id);
        if (maquinaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Maquina maquina = maquinaService.dtoParaMaquina(maquinaDTO);

        Maquina maquinaAtualizada = maquinaService.atualizarMaquina(maquina);
        MaquinaDTO maquinaAtualizadaDTO = maquinaService.maquinaParaDTO(maquinaAtualizada);

        return ResponseEntity.ok(maquinaAtualizadaDTO);
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
    public ResponseEntity<List<MaquinaDTO>> buscarPorCodPatrimonial(@PathVariable String codPatrimonial) {
        List<Maquina> maquinas = maquinaService.buscarPorCodPatrimonial(codPatrimonial);
        List<MaquinaDTO> maquinasDTO = new ArrayList<>();
        for(Maquina maquina : maquinas){
            maquinasDTO.add(maquinaService.maquinaParaDTO(maquina));
        }
        return ResponseEntity.ok(maquinasDTO);
    }

    // Endpoint para buscar máquinas pelo número de série
    @GetMapping("/buscar/num-serie/{numSerie}")
    public ResponseEntity<List<MaquinaDTO>> buscarPorNumSerie(@PathVariable String numSerie) {
        List<Maquina> maquinas = maquinaService.buscarPorNumSerie(numSerie);
        List<MaquinaDTO> maquinasDTO = new ArrayList<>();
        for(Maquina maquina : maquinas){
            maquinasDTO.add(maquinaService.maquinaParaDTO(maquina));
        }
        return ResponseEntity.ok(maquinasDTO);
    }

}
