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
    private ManutencaoAgendadaRepository repository;

    public List<ManutencaoAgendada> findAll() {
        return repository.findAll();
    }

    public Optional<ManutencaoAgendada> findById(Integer id) {
        return repository.findById(id);
    }

    public ManutencaoAgendada save(ManutencaoAgendada manutencaoAgendada) {
        return repository.save(manutencaoAgendada);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
