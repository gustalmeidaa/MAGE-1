package MAGE.mage.service;

import MAGE.mage.model.Movimentacao;
import MAGE.mage.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository repository;

    public List<Movimentacao> findAll() {
        return repository.findAll();
    }

    public Optional<Movimentacao> findById(Integer id) {
        return repository.findById(id);
    }

    public Movimentacao save(Movimentacao movimentacao) {
        return repository.save(movimentacao);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
