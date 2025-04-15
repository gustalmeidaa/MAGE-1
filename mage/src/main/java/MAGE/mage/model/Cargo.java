package MAGE.mage.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cargo")
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cargo")
    private Integer idCargo;
    @Column(name = "nome_cargo", length = 45)
    private String nomeCargo;
    @Column(name = "salario")
    private BigDecimal salario;
    @Column(name = "setor", length = 45)
    private String setor;
    @Column(name = "is_administrador")
    private Boolean isAdministrador;

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getNomeCargo() {
        return nomeCargo;
    }

    public void setNomeCargo(String nomeCargo) {
        this.nomeCargo = nomeCargo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public Boolean getAdministrador() {
        return isAdministrador;
    }

    public void setAdministrador(Boolean administrador) {
        isAdministrador = administrador;
    }
}
