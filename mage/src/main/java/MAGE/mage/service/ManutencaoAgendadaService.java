package MAGE.mage.service;

import MAGE.mage.model.ManutencaoAgendada;
import MAGE.mage.repository.ManutencaoAgendadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManutencaoAgendadaService {

    @Autowired
    private LogService logService;

    @Autowired
    private ManutencaoAgendadaRepository repository;

    public List<ManutencaoAgendada> findAll() {
        return repository.findAll();
    }

    public Optional<ManutencaoAgendada> findById(Integer id) {
        return repository.findById(id);
    }

    public ManutencaoAgendada save(ManutencaoAgendada manutencaoAgendada, String loginUsuario) {
        validarCusto(manutencaoAgendada);
        ManutencaoAgendada savedManutencaoAgendada = repository.save(manutencaoAgendada);
        logService.addLog("INSERT", "", savedManutencaoAgendada.toString(), loginUsuario);
        return savedManutencaoAgendada;
    }

    public void deleteById(Integer id, String loginUsuario) {
        Optional<ManutencaoAgendada> manutencaoAgendada = repository.findById(id);
        String oldData = manutencaoAgendada.isPresent() ? manutencaoAgendada.get().toString() : "";

        repository.deleteById(id);

        // Log de deleção
        logService.addLog("DELETE", oldData, "", loginUsuario);
    }

    private void validarCusto(ManutencaoAgendada manutencaoAgendada) {
        if (manutencaoAgendada.getCustoManutencao() == null || manutencaoAgendada.getCustoManutencao().signum() < 0) {
            throw new IllegalArgumentException("O custo de manutenção deve ser um valor positivo.");
        }
    }
}
