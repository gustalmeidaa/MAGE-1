package MAGE.mage.service;

import MAGE.mage.model.Maquina;
import MAGE.mage.repository.MaquinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaquinaService {

    @Autowired
    private MaquinaRepository maquinaRepository;

    public Maquina cadastrarMaquina(Maquina maquina) {
        return maquinaRepository.save(maquina);
    }
}
