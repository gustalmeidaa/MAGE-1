package MAGE.mage.service;

import MAGE.mage.dto.MaquinaDTO;
import MAGE.mage.dto.MovimentacaoDTO;
import MAGE.mage.model.Funcionario;
import MAGE.mage.model.Maquina;
import MAGE.mage.model.Movimentacao;
import MAGE.mage.repository.MaquinaRepository;
import MAGE.mage.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimentacaoService {

    @Autowired
    private LogService logService;

    @Autowired
    private MovimentacaoRepository repository;
    @Autowired
    private MaquinaRepository maquinaRepository;

    public Movimentacao dtoParaMovimentacao(MovimentacaoDTO movimentacaoDTO) {
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setTipo(movimentacaoDTO.tipo());
        movimentacao.setOrigem(movimentacaoDTO.origem());
        movimentacao.setDestino(movimentacaoDTO.destino());
        movimentacao.setData(movimentacaoDTO.data());

        if (movimentacaoDTO.idMaquinaMovimentada() != null) {
            Optional<Maquina> maquina = maquinaRepository.findById(movimentacaoDTO.idMaquinaMovimentada());
            maquina.ifPresent(movimentacao::setMaquinaMovimentada);
        }

        return movimentacao;
    }


    public List<Movimentacao> findAll() {
        return repository.findAll();
    }

    public Optional<Movimentacao> findById(Integer id) {
        return repository.findById(id);
    }

    public Movimentacao save(Movimentacao movimentacao, String loginUsuario) {
        Movimentacao savedMovimentacao = repository.save(movimentacao);

        logService.addLog("INSERT", "", savedMovimentacao.toString(), loginUsuario);

        return savedMovimentacao;
    }

    public Movimentacao update(Movimentacao movimentacao, String loginUsuario) {
        Optional<Movimentacao> oldMovimentacao = repository.findById(movimentacao.getIdMovimentacoes());
        String oldData = oldMovimentacao.toString();
        Movimentacao savedMovimentacao = repository.save(movimentacao);

        logService.addLog("UPDATE", oldData, savedMovimentacao.toString(), loginUsuario);

        return savedMovimentacao;
    }


    public void deleteById(Integer id, String loginUsuario) {
        Optional<Movimentacao> movimentacao = repository.findById(id);
        String oldData = movimentacao.isPresent() ? movimentacao.get().toString() : "";

        repository.deleteById(id);

        // Log de deleção
        logService.addLog("DELETE", oldData, "", loginUsuario);
    }

}
