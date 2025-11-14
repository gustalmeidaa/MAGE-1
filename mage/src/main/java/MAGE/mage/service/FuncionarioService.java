package MAGE.mage.service;

import MAGE.mage.dto.FuncionarioDTO;
import MAGE.mage.model.Funcionario;
import MAGE.mage.model.Setor;
import MAGE.mage.repository.FuncionarioRepository;
import MAGE.mage.repository.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

//    private final FuncionarioRepository funcionarioRepository;
//    private final SetorRepository setorRepository;

    @Autowired
    @Lazy
    private FuncionarioRepository funcionarioRepository;


    @Autowired
    private SetorRepository setorRepository;
//    public FuncionarioService(FuncionarioRepository funcionarioRepository, SetorRepository setorRepository) {
//        this.funcionarioRepository = funcionarioRepository;
//        this.setorRepository = setorRepository;
//    }

    @Autowired
    private LogService logService;

    public Funcionario createFuncionario(FuncionarioDTO funcionarioDTO, String loginUsuario) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNomeFuncionario(funcionarioDTO.nomeFuncionario());

        // Lógica para associar o setor pelo nome
        if (funcionarioDTO.nomeSetor() != null) {
            Optional<Setor> setor = setorRepository.findByNomeSetor(funcionarioDTO.nomeSetor());
            if (setor.isPresent()) {
                funcionario.setSetor(setor.get());
            } else {
                throw new RuntimeException("Setor não encontrado com o nome " + funcionarioDTO.nomeSetor());
            }
        }
        Funcionario savedFuncionario = funcionarioRepository.save(funcionario);
        logService.addLog("INSERT", "", savedFuncionario.toString(), loginUsuario);
        return savedFuncionario;
    }

    public List<Funcionario> getAllFuncionarios() {
        return funcionarioRepository.findAll();
    }

    public Optional<Funcionario> getFuncionarioById(Integer id) {
        return funcionarioRepository.findById(id);
    }

    public Funcionario updateFuncionario(Integer id, FuncionarioDTO funcionarioDTO, String loginUsuario) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o id " + id));

        String oldData = funcionario.toString();
        funcionario.setNomeFuncionario(funcionarioDTO.nomeFuncionario());

        // Atualizando o setor, se fornecido
        if (funcionarioDTO.nomeSetor() != null) {
            Optional<Setor> setor = setorRepository.findByNomeSetor(funcionarioDTO.nomeSetor());
            setor.ifPresentOrElse(funcionario::setSetor,
                    () -> { throw new RuntimeException("Setor não encontrado com o nome " + funcionarioDTO.nomeSetor()); });
        }

        Funcionario savedFuncionario = funcionarioRepository.save(funcionario);
        logService.addLog("UPDATE", oldData, savedFuncionario.toString(), loginUsuario);
        return savedFuncionario;
    }

    public void deleteFuncionario(Integer id, String loginUsuario) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com o id " + id));
        System.out.println("\n O funcionário foi pesquisado: "+ funcionario.toString());
        funcionarioRepository.deleteById(id);
        System.out.println("\n Foi deletado e virá o log \n");
        logService.addLog("DELETE", funcionario.toString(), "", loginUsuario);
    }

}
