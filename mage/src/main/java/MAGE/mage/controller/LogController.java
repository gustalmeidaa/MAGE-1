package MAGE.mage.controller;

import MAGE.mage.model.Log;
import MAGE.mage.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping
    public ResponseEntity<Log> createLog(@RequestBody Log log) {
        logService.addLog(log.getOperacao(), log.getDadosAntigos(), log.getDadosNovos(), log.getLoginUsuario());
        return ResponseEntity.ok(log);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Log> getLogById(@PathVariable Integer id) {
        return logService.findById(id)
                .map(log -> ResponseEntity.ok(log))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Log>> getAllLogs() {
        List<Log> logs = logService.findAll();
        return ResponseEntity.ok(logs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Log> updateLog(@PathVariable Integer id, @RequestBody Log log) {
        Log updatedLog = logService.updateLog(id, log);
        if (updatedLog != null) {
            return ResponseEntity.ok(updatedLog);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable Integer id) {
        logService.deleteLogById(id);
        return ResponseEntity.noContent().build();
    }
}
