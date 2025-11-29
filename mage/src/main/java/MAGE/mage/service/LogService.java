package MAGE.mage.service;

import MAGE.mage.model.Administrador;
import MAGE.mage.model.Log;
import MAGE.mage.repository.AdministradorRepository;
import MAGE.mage.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogService {

    private final LogRepository logRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public List<Log> findAll() {
        return logRepository.findAll();
    }

    public Optional<Log> findById(Integer id) {
        return logRepository.findById(id);
    }

    public void addLog(String operacao, String dadosAntigos, String dadosNovos, String loginUsuario) {
        Log logEntry = new Log(operacao, dadosAntigos, dadosNovos, loginUsuario);
//        Optional<Administrador> administradorOptional = administradorRepository.findById(loginUsuario);
//        if (administradorOptional.isEmpty()){
//            throw new UsernameNotFoundException("Usuário com login "+loginUsuario+" não existe");
//        }
        logRepository.save(logEntry);
    }

    public Log updateLog(Integer id, Log log) {
        Optional<Log> optionalLog = logRepository.findById(id);

        if (optionalLog.isPresent()) {
            Log existingLog = optionalLog.get();
            existingLog.setOperacao(log.getOperacao());
            existingLog.setDadosAntigos(log.getDadosAntigos());
            existingLog.setDadosNovos(log.getDadosNovos());

            existingLog.setLoginUsuario(log.getLoginUsuario());

            return logRepository.save(existingLog);
        }

        return null;
    }

    public void deleteLogById(Integer id) {
        logRepository.deleteById(id);
    }
}
