package MAGE.mage.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Table(name = "Maquina")
public class Maquina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_maquina")
    private Long idMaquina;
    @Column(name = "cod_patrimonial", length = 45, nullable = false)
    private String codPatrimonial;
    @Column(name = "num_serie", length = 45, nullable = false)
    private String numSerie;
    @Column(name = "valor", nullable = false) // Podemos adicionar o número de casas decimais se necessário
    private BigDecimal valor;
    @Column(name = "id_responsavel", nullable = true)
    private Integer idResponsavel;
    @Column(name = "localizacao", length = 45)
    private String localizacao;

    public Long getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(Long idMaquina) {
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

    public Integer getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(Integer idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void agendarManutencoes(LocalDate dataManutencao){}
    public void registrarManutencao(){}
}
