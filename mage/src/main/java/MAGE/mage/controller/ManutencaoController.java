package MAGE.mage.controller;

import MAGE.mage.dto.ManutencaoDTO;
import MAGE.mage.model.Funcionario;
import MAGE.mage.model.Manutencao;
import MAGE.mage.model.Maquina;
import MAGE.mage.repository.FuncionarioRepository;
import MAGE.mage.repository.MaquinaRepository;
import MAGE.mage.security.TokenService;
import MAGE.mage.service.ManutencaoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/manutencoes")
public class ManutencaoController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ManutencaoService manutencaoService;

    @Autowired
    private MaquinaRepository maquinaRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Manutencao dtoParaManutencao(ManutencaoDTO manutencaoDTO) {
        Manutencao manutencao = new Manutencao();
        manutencao.setData(manutencaoDTO.data());
        manutencao.setTipoManutencao(manutencaoDTO.tipoManutencao());
        manutencao.setProcedimentos(manutencaoDTO.procedimentos());

        // Mapeando o custo da manutenção
        manutencao.setCustoManutencao(manutencaoDTO.custoManutencao());

        if (manutencaoDTO.idMaquina() != null) {
            Optional<Maquina> maquinaOptional = maquinaRepository.findById(manutencaoDTO.idMaquina());
            if (maquinaOptional.isPresent()) {
                manutencao.setIdMaquina(maquinaOptional.get());
            }
        }

        if (manutencaoDTO.idFuncionario() != null) {
            Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(manutencaoDTO.idFuncionario());
            if (funcionarioOptional.isPresent()) {
                manutencao.setIdFuncionario(funcionarioOptional.get());
            }
        }
        return manutencao;
    }

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
    public ResponseEntity<Manutencao> createManutencao(@RequestBody ManutencaoDTO manutencaoDTO, HttpServletRequest request) {
        String loginUsuario = tokenService.getCurrentUserLogin(request);

        Manutencao manutencao = this.dtoParaManutencao(manutencaoDTO);

        Manutencao manutencaoSalva = manutencaoService.save(manutencao, loginUsuario);
        return ResponseEntity.ok(manutencaoSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Manutencao> updateManutencao(@PathVariable Integer id, @RequestBody ManutencaoDTO manutencaoDTO, HttpServletRequest request) {
        String loginUsuario = tokenService.getCurrentUserLogin(request);

        if (manutencaoService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Manutencao manutencao = this.dtoParaManutencao(manutencaoDTO);
        manutencao.setIdHistoricoManutencoes(id); // Definindo a ID para atualização

        Manutencao updatedManutencao = manutencaoService.update(manutencao, loginUsuario);
        return ResponseEntity.ok(updatedManutencao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManutencao(@PathVariable Integer id, HttpServletRequest request) {
        String loginUsuario = tokenService.getCurrentUserLogin(request);
        if (!manutencaoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        manutencaoService.deleteById(id, loginUsuario);
        return ResponseEntity.noContent().build();
    }
}

