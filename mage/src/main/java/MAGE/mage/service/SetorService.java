package MAGE.mage.service;

import MAGE.mage.model.Setor;
import MAGE.mage.repository.SetorRepository;
import MAGE.mage.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SetorService {

    private final SetorRepository setorRepository;
    private final LogService logService;

    @Autowired
    public SetorService(SetorRepository setorRepository, LogService logService) {
        this.setorRepository = setorRepository;
        this.logService = logService;
    }

    public List<Setor> findAll() {
        return setorRepository.findAll();
    }

    public Optional<Setor> findById(Integer id) {
        return setorRepository.findById(id);
    }

    public Setor save(Setor setor, String loginUsuario) {
        Setor savedSetor = setorRepository.save(setor);

        logService.addLog("INSERT", "", savedSetor.toString(), loginUsuario);

        return savedSetor;

    }

    public Setor update(Setor setor, String loginUsuario, Integer id){
        Optional<Setor> optionalSetoretor = setorRepository.findById(setor.getIdSetor());
        String oldData = optionalSetoretor.isPresent() ? optionalSetoretor.get().toString() : "";
        Setor savedSetor = setorRepository.save(setor);

        logService.addLog("UPDATE", oldData, savedSetor.toString(), loginUsuario);

        return savedSetor;
    }

    public void deleteById(Integer id, String loginUsuario) {
        Optional<Setor> setor = setorRepository.findById(id);
        String oldData = setor.isPresent() ? setor.get().toString() : "";

        setorRepository.deleteById(id);
        logService.addLog("DELETE", oldData, "", loginUsuario);
    }
}
