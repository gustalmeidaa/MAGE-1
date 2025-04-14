package MAGE.mage.controller;

import MAGE.mage.model.Maquina;
import MAGE.mage.service.MaquinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maquinas")
public class MaquinaController {

    @Autowired
    private MaquinaService maquinaService;

    @PostMapping
    public ResponseEntity<Maquina> cadastrarMaquina(@RequestBody Maquina maquina) {
        Maquina novaMaquina = maquinaService.cadastrarMaquina(maquina);
        return ResponseEntity.ok(novaMaquina);
    }
}
