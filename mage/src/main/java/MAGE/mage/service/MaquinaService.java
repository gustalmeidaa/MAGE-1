package MAGE.mage.service;

import MAGE.mage.model.Maquina;
import MAGE.mage.repository.MaquinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
}
