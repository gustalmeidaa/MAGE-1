package MAGE.mage.service;

import MAGE.mage.model.Funcionario;
import MAGE.mage.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionario createFuncionario(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> getAllFuncionarios() {
        return funcionarioRepository.findAll();
    }

    public Optional<Funcionario> getFuncionarioById(Integer id) {
        return funcionarioRepository.findById(id);
    }

    public Funcionario updateFuncionario(Integer id, Funcionario funcionarioDetails) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o id " + id));

        funcionario.setNomeFuncionario(funcionarioDetails.getNomeFuncionario());
        return funcionarioRepository.save(funcionario);
    }

    public void deleteFuncionario(Integer id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o id " + id));
        funcionarioRepository.delete(funcionario);
    }
}
