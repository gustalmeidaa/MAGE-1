package MAGE.mage.service;

import MAGE.mage.model.Log;
import MAGE.mage.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    private final LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void addLog(String operacao, String dadosAntigos, String dadosNovos, String loginUsuario) {
        Log logEntry = new Log(operacao, dadosAntigos, dadosNovos, loginUsuario);
        logRepository.save(logEntry);
    }
}
