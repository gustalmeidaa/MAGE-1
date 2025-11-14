package MAGE.mage.service;

import MAGE.mage.model.Manutencao;
import MAGE.mage.model.Maquina;
import MAGE.mage.repository.ManutencaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ManutencaoService {

    @Autowired
    private LogService logService;

    @Autowired
    private ManutencaoRepository manutencaoRepository;

    public List<Manutencao> findAll() {
        return manutencaoRepository.findAll();
    }

    public Optional<Manutencao> findById(Integer id) {
        return manutencaoRepository.findById(id);
    }

    public Manutencao save(Manutencao manutencao, String loginUsuario) {
        Manutencao savedManutencao = manutencaoRepository.save(manutencao);
        logService.addLog("INSERT", "", savedManutencao.toString(), loginUsuario); // Registro de INSERT
        return savedManutencao;
    }

    public Manutencao update(Manutencao manutencao, String loginUsuario) {
        Manutencao updatedManutencao = manutencaoRepository.save(manutencao);
        logService.addLog("UPDATE", manutencao.toString(), updatedManutencao.toString(), loginUsuario); // Registro de UPDATE
        return updatedManutencao;
    }

    public void deleteById(Integer id, String loginUsuario) {
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Manutenção não encontrada com ID " + id));
        manutencaoRepository.deleteById(id);
        logService.addLog("DELETE", manutencao.toString(), "", loginUsuario); // Registro de DELETE
    }
}
