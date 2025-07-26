package MAGE.mage.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "administrador")
public class Administrador implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adm")
    private Integer idAdm;
    @Column(name = "login", length = 45)
    private String login;
    @Column(name = "senha", length = 60)
    private String senha;

//    public Administrador(String login, String encryptedPassword) {
//        this.setLogin(login);
//        this.setSenha(encryptedPassword);
//    }

    public Administrador() {}


    public Integer getIdAdm() {
        return idAdm;
    }

    public void setIdAdm(Integer idAdm) {
        this.idAdm = idAdm;
    }

//    public String getLogin() {
//        return login;
//    }

    public void setLogin(String login) {
        this.login = login;
    }

//    public String getSenha() {
//        return senha;
//    }

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Se você tiver um campo que indica a data de expiração da conta, use-o
        // Caso contrário, retorne true para indicar que a conta não expirou
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Se você tiver um campo que indica se a conta está bloqueada, use-o
        // Caso contrário, retorne true para indicar que a conta não está bloqueada
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Se você tiver um campo que indica a data de expiração das credenciais, use-o
        // Caso contrário, retorne true para indicar que as credenciais não expiraram
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Se você tiver um campo que indica se a conta está habilitada, use-o
        // Caso contrário, retorne true para indicar que a conta está habilitada
        return true;
    }
}
