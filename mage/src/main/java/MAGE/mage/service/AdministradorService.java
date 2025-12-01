package MAGE.mage.service;

import MAGE.mage.dto.AdministradorDto;
import MAGE.mage.model.Administrador;
import MAGE.mage.model.Funcionario;
import MAGE.mage.model.Maquina;
import MAGE.mage.repository.AdministradorRepository;
import MAGE.mage.repository.FuncionarioRepository;
import MAGE.mage.repository.MaquinaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {

    @Autowired
    private LogService logService;


    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private MaquinaRepository maquinaRepository;

    public List<Administrador> findAll() {
        return administradorRepository.findAll();
    }

    public Optional<Administrador> findById(String login) {
        return administradorRepository.findById(login);
    }

    public Administrador create(AdministradorDto administradorDto, String loginUsuario) {

        if (this.administradorRepository.findByLogin(administradorDto.login()) != null) return null;
        String encryptedPassword = new BCryptPasswordEncoder().encode(administradorDto.senha());
        Administrador administrador = new Administrador(administradorDto.login(), encryptedPassword);
        Administrador savedAdministrador = this.administradorRepository.save(administrador);
        logService.addLog("INSERT", "", savedAdministrador.toString(), loginUsuario);
//        if(administradorRepository.findByLogin("admin0") != null){
//            this.delete("admin0", loginUsuario);
//        }
        return savedAdministrador;
    }

    public Administrador update(Administrador administrador, String loginUsuario) {
        if (this.administradorRepository.findByLogin(administrador.getUsername()) != null){
            String oldData = administrador.toString();
            String encryptedPassword = new BCryptPasswordEncoder().encode(administrador.getSenha());
            Administrador administradorNovo = new Administrador(administrador.getLogin(), encryptedPassword);
            Administrador updatedAdministrador = administradorRepository.save(administradorNovo);
            logService.addLog("UPDATE", oldData, updatedAdministrador.toString(), loginUsuario);
            return updatedAdministrador;
        } else {
            return null;
        }
    }

    public void delete(String login, String loginUsuario) {
        Administrador administrador = administradorRepository.findById(login)
                .orElseThrow(() -> new EntityNotFoundException("Administrador não encontrado com o login " + login));
        administradorRepository.deleteById(login);
        logService.addLog("DELETE", administrador.toString(), "", loginUsuario);
    }

    public void atribuirUsuario(Integer idMaquina, Integer idFuncionario, String loginUsuario) {
        Optional<Maquina> maquinaOpt = maquinaRepository.findById(idMaquina);

        if (maquinaOpt.isPresent()) {
            Maquina maquina = maquinaOpt.get();
            if (idFuncionario != null) {
                Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(idFuncionario);
                if (funcionarioOpt.isPresent()) {
                    Funcionario funcionario = funcionarioOpt.get();
                    maquina.setResponsavel(funcionario);
                } else {
                    throw new IllegalArgumentException("Funcionário não encontrado com ID: " + idFuncionario);
                }
            } else {
                maquina.setResponsavel(null);
            }
            maquinaRepository.save(maquina);
            logService.addLog("ATUALIZAÇÃO_RESPONSAVEL", "Máquina ID: " + idMaquina, "Novo Responsável ID: " + idFuncionario, loginUsuario);
        } else {
            throw new IllegalArgumentException("Máquina não encontrada com ID: " + idMaquina);
        }
    }

}
