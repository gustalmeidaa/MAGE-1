package MAGE.mage.service;

import MAGE.mage.dto.MaquinaDTO;
import MAGE.mage.model.Funcionario;
import MAGE.mage.model.Maquina;
import MAGE.mage.repository.FuncionarioRepository;
import MAGE.mage.repository.MaquinaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MaquinaService {

    @Autowired
    private MaquinaRepository maquinaRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public MaquinaDTO maquinaParaDTO(Maquina maquina){
//        Integer idResponsavel = ( maquina.getResponsavel() != null ) ? maquina.getResponsavel().getIdFuncionario() : null;
        Integer idResponsavel = null;
        if (maquina.getResponsavel() != null) {
            idResponsavel = maquina.getResponsavel().getIdFuncionario();
        }
        return new MaquinaDTO(
                maquina.getCodPatrimonial(),
                maquina.getNumSerie(),
                maquina.getValor(),
                idResponsavel,
                maquina.getLocalizacao(),
                maquina.getStatus(),
                maquina.getDescricao()
        );
    }
    public Maquina dtoParaMaquina(MaquinaDTO maquinaDTO){
        Maquina maquina = new Maquina();
        maquina.setCodPatrimonial(maquinaDTO.codPatrimonial());
        maquina.setNumSerie(maquinaDTO.numSerie());
        maquina.setValor(maquinaDTO.valor());
        maquina.setLocalizacao(maquinaDTO.localizacao());
        maquina.setStatus(maquinaDTO.status());

        if(maquinaDTO.idResponsavel() != null){
            Optional<Funcionario> responsavel = funcionarioRepository.findById(maquinaDTO.idResponsavel());
            responsavel.ifPresent(maquina::setResponsavel);
        }
        return maquina;
    }

    // CRUD
    public Maquina cadastrarMaquina(Maquina maquina) {
        validarValorPositivo(maquina.getValor());
        return maquinaRepository.save(maquina);
    }

    public List<Maquina> listarMaquinas() {
        return maquinaRepository.findAll();
    }

    public Optional<Maquina> buscarMaquina(Integer id) {
        return maquinaRepository.findById(id);
    }

    public Maquina atualizarMaquina(Maquina maquina) {
        validarValorPositivo(maquina.getValor());
        return maquinaRepository.save(maquina);
    }

    public void excluirMaquina(Integer id) {
        maquinaRepository.deleteById(id);
    }

    public long contarMaquinasAtivas() {
        return maquinaRepository.countAtivas();
    }

    public long contarMaquinasInativas() {
        return maquinaRepository.countInativas();
    }

    public long contarMaquinasEmManutencao() {
        return maquinaRepository.countEmManutencao();
    }

    public StatusCounts getStatusCounts() {
        long ativas = contarMaquinasAtivas();
        long inativas = contarMaquinasInativas();
        long emManutencao = contarMaquinasEmManutencao();
        return new StatusCounts(ativas, inativas, emManutencao);
    }

    public static class StatusCounts {
        private long ativas;
        private long inativas;
        private long emManutencao;

        public StatusCounts(long ativas, long inativas, long emManutencao) {
            this.ativas = ativas;
            this.inativas = inativas;
            this.emManutencao = emManutencao;
        }

        public long getAtivas() {
            return ativas;
        }

        public long getInativas() {
            return inativas;
        }

        public long getEmManutencao() {
            return emManutencao;
        }
    }

    public List<Maquina> buscarPorCodPatrimonial(String codPatrimonial) {
        return maquinaRepository.findByCodPatrimonial(codPatrimonial);
    }

    public List<Maquina> buscarPorNumSerie(String numSerie) {
        return maquinaRepository.findByNumSerie(numSerie);
    }

    private void validarValorPositivo(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor deve ser positivo.");
        }
    }
}
