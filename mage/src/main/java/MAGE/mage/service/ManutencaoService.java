package MAGE.mage.service;

import MAGE.mage.model.Manutencao;
import MAGE.mage.model.Maquina;
import MAGE.mage.repository.ManutencaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public void agendarManutencao(Maquina maquina, LocalDateTime data, String tipoManutencao, String procedimentos) {
        Manutencao manutencao = new Manutencao();
        manutencao.setData(data);
        manutencao.setTipoManutencao(tipoManutencao);
        manutencao.setProcedimentos(procedimentos);
        manutencao.setIdMaquina(maquina);

        manutencaoRepository.save(manutencao);
    }
}
