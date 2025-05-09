package MAGE.mage.controller;

import MAGE.mage.dto.ManutencaoDTO;
import MAGE.mage.model.Funcionario;
import MAGE.mage.model.Manutencao;
import MAGE.mage.model.Maquina;
import MAGE.mage.repository.FuncionarioRepository;
import MAGE.mage.repository.MaquinaRepository;
import MAGE.mage.service.ManutencaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/manutencoes")
public class ManutencaoController {

    @Autowired
    private ManutencaoService manutencaoService;
    @Autowired
    private MaquinaRepository maquinaRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;

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
    public ResponseEntity<Manutencao> createManutencao(@RequestBody ManutencaoDTO manutencaoDTO) {
        Manutencao manutencao = new Manutencao();
        manutencao.setData(manutencaoDTO.data());
        manutencao.setTipoManutencao(manutencaoDTO.tipoManutencao());
        manutencao.setProcedimentos(manutencaoDTO.procedimentos());

        if (manutencaoDTO.idMaquina() != null){
            Optional<Maquina> maquinaOptional = maquinaRepository.findById(manutencaoDTO.idMaquina());
            if (maquinaOptional.isEmpty()){
                return ResponseEntity.badRequest().build();
            }
            manutencao.setIdMaquina(maquinaOptional.get());
        }

        if (manutencaoDTO.idFuncionario() != null){
            Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(manutencaoDTO.idFuncionario());
            if (funcionarioOptional.isEmpty()){
                return ResponseEntity.badRequest().build();
            }
            manutencao.setIdFuncionario(funcionarioOptional.get());
        }

        Manutencao manutencaoSalva = manutencaoService.save(manutencao);
        return ResponseEntity.ok(manutencaoSalva);
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
