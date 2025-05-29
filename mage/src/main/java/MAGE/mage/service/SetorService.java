package MAGE.mage.service;

import MAGE.mage.model.Setor;
import MAGE.mage.repository.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SetorService {

    private final SetorRepository setorRepository;

    @Autowired
    public SetorService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    public List<Setor> findAll() {
        return setorRepository.findAll();
    }

    public Optional<Setor> findById(Integer id) {
        return setorRepository.findById(id);
    }

    public Setor save(Setor setor) {
        return setorRepository.save(setor);
    }

    public void deleteById(Integer id) {
        setorRepository.deleteById(id);
    }
}
