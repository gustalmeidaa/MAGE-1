package MAGE.mage.service;

import MAGE.mage.model.Manutencao;
import MAGE.mage.repository.ManutencaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManutencaoService {

    @Autowired
    private ManutencaoRepository manutencaoRepository;

    public List<Manutencao> findAll() {
        return manutencaoRepository.findAll();
    }

    public Optional<Manutencao> findById(Integer id) {
        return manutencaoRepository.findById(id);
    }

    public Manutencao save(Manutencao manutencao) {
        return manutencaoRepository.save(manutencao);
    }

    public void deleteById(Integer id) {
        manutencaoRepository.deleteById(id);
    }
}
