package MAGE.mage.controller;

import MAGE.mage.dto.MaquinaDTO;
import MAGE.mage.model.Funcionario;
import MAGE.mage.model.Maquina;
import MAGE.mage.repository.FuncionarioRepository;
import MAGE.mage.repository.MaquinaRepository;
import MAGE.mage.service.MaquinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/maquinas")
public class MaquinaController {

    @Autowired
    private MaquinaRepository maquinaRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @PostMapping
    public ResponseEntity<Maquina> criarMaquina(@RequestBody MaquinaDTO maquinaDTO) {
        Optional<Funcionario> responsavelOptional = funcionarioRepository.findById(maquinaDTO.idResponsavel());
        if (responsavelOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Funcionario responsavel = responsavelOptional.get();

        Maquina maquina = new Maquina();
        maquina.setCodPatrimonial(maquinaDTO.codPatrimonial());
        maquina.setNumSerie(maquinaDTO.numSerie());
        maquina.setValor(maquinaDTO.valor());
        maquina.setResponsavel(responsavel);
        maquina.setLocalizacao(maquinaDTO.localizacao());

        Maquina maquinaSalva = maquinaRepository.save(maquina);

        return ResponseEntity.ok(maquinaSalva);
    }
}
