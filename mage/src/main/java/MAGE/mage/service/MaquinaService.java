package MAGE.mage.service;

import MAGE.mage.model.Maquina;
import MAGE.mage.repository.MaquinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaquinaService {

    @Autowired
    private MaquinaRepository maquinaRepository;

    public Maquina cadastrarMaquina(Maquina maquina) {
        return maquinaRepository.save(maquina);
    }

    public List<Maquina> listarMaquinas() {
        return maquinaRepository.findAll();
    }

    public Optional<Maquina> buscarMaquina(Integer id) {
        return maquinaRepository.findById(id);
    }

    public Maquina atualizarMaquina(Maquina maquina) {
        return maquinaRepository.save(maquina);
    }

    public void excluirMaquina(Integer id) {
        maquinaRepository.deleteById(id);
    }
}
