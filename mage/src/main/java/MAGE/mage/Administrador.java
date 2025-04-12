package MAGE.mage;

import MAGE.mage.model.Funcionario;
import MAGE.mage.model.Maquina;

import java.util.List;

public class Administrador extends Funcionario {
    private String login;
    private String senha;

    public void cadastrarMaquina(){}
    public List<Maquina> buscarMaquinas(){return null;}
    public void editarMaquina(){}
    public List<Funcionario> buscarFuncionarios(){return null;}
    public void editarUsuario(){}
    public void atribuirUsuario(Maquina maquina){}
}
