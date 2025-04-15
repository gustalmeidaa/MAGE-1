package MAGE.mage.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Table(name = "maquina")
public class Maquina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_maquina")
    private Integer idMaquina;
    @Column(name = "cod_patrimonial", length = 45, nullable = false)
    private String codPatrimonial;
    @Column(name = "num_serie", length = 45, nullable = false)
    private String numSerie;
    @Column(name = "valor", nullable = false) // Podemos adicionar o número de casas decimais se necessário
    private BigDecimal valor;
    @ManyToOne
    @JoinColumn(name = "id_responsavel")
    private Funcionario responsavel;
    @Column(name = "localizacao", length = 45)
    private String localizacao;

    public Integer getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(Integer idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getCodPatrimonial() {
        return codPatrimonial;
    }

    public void setCodPatrimonial(String codPatrimonial) {
        this.codPatrimonial = codPatrimonial;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Funcionario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Funcionario responsavel) {
        this.responsavel = responsavel;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void agendarManutencoes(LocalDate dataManutencao){} // Serão organizados posteriormente
    public void registrarManutencao(){}
}
