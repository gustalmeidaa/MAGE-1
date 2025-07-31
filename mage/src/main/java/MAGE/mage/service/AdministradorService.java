package MAGE.mage.service;

import MAGE.mage.dto.AdministradorDto;
import MAGE.mage.model.Administrador;
import MAGE.mage.repository.AdministradorRepository;
import MAGE.mage.repository.FuncionarioRepository;
import MAGE.mage.repository.MaquinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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

    public Administrador create (@RequestBody AdministradorDto administradorDto){
        if (this.administradorRepository.findByLogin(administradorDto.login()) != null) return null;
        String encryptedPassword = new BCryptPasswordEncoder().encode(administradorDto.senha());
        Administrador administrador = new Administrador(administradorDto.login(), encryptedPassword);
        return this.administradorRepository.save(administrador);
    }

    public List<Administrador> findAll() {
        return administradorRepository.findAll();
    }

    public Optional<Administrador> findById(String login) {
        return administradorRepository.findById(login);
    }

    public Administrador update(Administrador administrador) {
        if (this.administradorRepository.findByLogin(administrador.getUsername()) != null){
            return administradorRepository.save(administrador);
        }
        else {
            return null;
        }
    }

    public void delete(String login) {
        administradorRepository.deleteById(login);
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
