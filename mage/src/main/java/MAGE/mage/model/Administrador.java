package MAGE.mage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "administrador")
public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adm")
    private Integer idAdm;
    @Column(name = "login", length = 45)
    private String login;
    @Column(name = "senha", length = 60)
    private String senha;

    public Integer getIdAdm() {
        return idAdm;
    }

    public void setIdAdm(Integer idAdm) {
        this.idAdm = idAdm;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void atribuirUsuario(Maquina maquina, Funcionario funcionario) {
        if (maquina != null && funcionario != null) {
//            maquina.setIdResponsavel(funcionario.getIdFuncionario());
        } else {
            throw new IllegalArgumentException("Máquina ou Funcionário não podem ser nulos.");
        }
    }
}
