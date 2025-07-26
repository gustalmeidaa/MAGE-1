package MAGE.mage.service;

import MAGE.mage.model.Administrador;
import MAGE.mage.model.Funcionario;
import MAGE.mage.model.Maquina;
import MAGE.mage.repository.AdministradorRepository;
import MAGE.mage.repository.FuncionarioRepository;
import MAGE.mage.repository.MaquinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {


    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private MaquinaRepository maquinaRepository;

    public Administrador create(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    public List<Administrador> findAll() {
        return administradorRepository.findAll();
    }

    public Optional<Administrador> findById(Integer id) {
        return administradorRepository.findById(id);
    }

    public Administrador update(Integer id, Administrador administrador) {
        administrador.setIdAdm(id);
        return administradorRepository.save(administrador);
    }

    public void delete(Integer id) {
        administradorRepository.deleteById(id);
    }

//    public void atribuirUsuario(Integer idMaquina, Integer idFuncionario) {
//        Optional<Maquina> maquinaOpt = maquinaRepository.findById(idMaquina);
//
//        if (maquinaOpt.isPresent()) {
//            Maquina maquina = maquinaOpt.get();
//
//            if (idFuncionario != null) {
//                Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(idFuncionario);
//                if (funcionarioOpt.isPresent()) {
//                    Funcionario funcionario = funcionarioOpt.get();
//                    Administrador administrador = new Administrador(); // A ser refatorado
//                    administrador.atribuirUsuario(maquina, funcionario);
//                } else {
//                    throw new IllegalArgumentException("Funcionário não encontrado com ID: " + idFuncionario);
//                }
//            } else {
//                maquina.setResponsavel(null);
//            }
//
//            maquinaRepository.save(maquina);
//        } else {
//            throw new IllegalArgumentException("Máquina não encontrada com ID: " + idMaquina);
//        }
//    }
}
