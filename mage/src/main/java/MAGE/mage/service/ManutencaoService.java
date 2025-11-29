package MAGE.mage.service;

import MAGE.mage.model.Manutencao;
import MAGE.mage.repository.ManutencaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        validarCusto(manutencao);
        Manutencao savedManutencao = manutencaoRepository.save(manutencao);
        logService.addLog("INSERT", "", savedManutencao.toString(), loginUsuario);
        return savedManutencao;
    }

    public Manutencao update(Manutencao manutencao, String loginUsuario) {
        validarCusto(manutencao);
        Manutencao updatedManutencao = manutencaoRepository.save(manutencao);
        logService.addLog("UPDATE", manutencao.toString(), updatedManutencao.toString(), loginUsuario);
        return updatedManutencao;
    }

    public void deleteById(Integer id, String loginUsuario) {
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Manutenção não encontrada com ID " + id));
        manutencaoRepository.deleteById(id);
        logService.addLog("DELETE", manutencao.toString(), "", loginUsuario); // Registro de DELETE
    }

    private void validarCusto(Manutencao manutencao) {
        if (manutencao.getCustoManutencao() == null || manutencao.getCustoManutencao().signum() < 0) {
            throw new IllegalArgumentException("O custo de manutenção deve ser um valor positivo.");
        }
    }
}
