package MAGE.mage.controller;

import MAGE.mage.dto.ManutencaoAgendadaDTO;
import MAGE.mage.model.ManutencaoAgendada;
import MAGE.mage.model.Maquina;
import MAGE.mage.repository.MaquinaRepository;
import MAGE.mage.security.TokenService;
import MAGE.mage.service.ManutencaoAgendadaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/manutencoes-agendadas")
public class ManutencaoAgendadaController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ManutencaoAgendadaService service;

    @Autowired
    private MaquinaRepository maquinaRepository;

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
    public ResponseEntity<ManutencaoAgendada> create(@RequestBody ManutencaoAgendadaDTO manutencaoAgendadaDTO, HttpServletRequest request) {
        String loginUsuario = tokenService.getCurrentUserLogin(request);
        ManutencaoAgendada manutencaoAgendada = new ManutencaoAgendada();
        manutencaoAgendada.setDataAgendada(manutencaoAgendadaDTO.dataAgendada());
        manutencaoAgendada.setTipoManutencao(manutencaoAgendadaDTO.tipoManutencao());
        manutencaoAgendada.setProcedimentos(manutencaoAgendadaDTO.procedimentos());

        // Mapeando o custo da manutenção
        manutencaoAgendada.setCustoManutencao(manutencaoAgendadaDTO.custoManutencao());

        if (manutencaoAgendadaDTO.idMaquina() != null) {
            Optional<Maquina> maquinaOptional = maquinaRepository.findById(manutencaoAgendadaDTO.idMaquina());
            if (maquinaOptional.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            manutencaoAgendada.setMaquina(maquinaOptional.get());
        }

        ManutencaoAgendada manutencaoSalva = service.save(manutencaoAgendada, loginUsuario);
        return ResponseEntity.ok(manutencaoSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManutencaoAgendada> update(@PathVariable Integer id, @RequestBody ManutencaoAgendadaDTO manutencaoAgendadaDTO, HttpServletRequest request) {
        String loginUsuario = tokenService.getCurrentUserLogin(request);
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ManutencaoAgendada manutencaoAgendada = new ManutencaoAgendada();
        manutencaoAgendada.setIdManutencaoAgendada(id);
        manutencaoAgendada.setDataAgendada(manutencaoAgendadaDTO.dataAgendada());
        manutencaoAgendada.setTipoManutencao(manutencaoAgendadaDTO.tipoManutencao());
        manutencaoAgendada.setProcedimentos(manutencaoAgendadaDTO.procedimentos());

        // Mapeando o custo da manutenção
        manutencaoAgendada.setCustoManutencao(manutencaoAgendadaDTO.custoManutencao());

        if (manutencaoAgendadaDTO.idMaquina() != null) {
            Optional<Maquina> maquinaOptional = maquinaRepository.findById(manutencaoAgendadaDTO.idMaquina());
            if (maquinaOptional.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            manutencaoAgendada.setMaquina(maquinaOptional.get());
        }

        ManutencaoAgendada updatedManutencaoAgendada = service.save(manutencaoAgendada, loginUsuario);
        return ResponseEntity.ok(updatedManutencaoAgendada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id, HttpServletRequest request) {
        String loginUsuario = tokenService.getCurrentUserLogin(request);
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id, loginUsuario); // Passar loginUsuario para garantir que o log seja registrado
        return ResponseEntity.noContent().build();
    }
}
