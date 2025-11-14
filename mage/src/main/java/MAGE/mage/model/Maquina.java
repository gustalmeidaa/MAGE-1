package MAGE.mage.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "maquina")
public class Maquina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_maquina")
    private Integer idMaquina;
    @Column(name = "cod_patrimonial", length = 45, nullable = false, unique = true)
    private String codPatrimonial;
    @Column(name = "num_serie", length = 45, nullable = false, unique = true)
    private String numSerie;
    @Column(name = "valor", nullable = false)
    private BigDecimal valor;
    @ManyToOne
    @JoinColumn(name = "id_responsavel")
    @JsonBackReference // evita a serialização
    private Funcionario responsavel;
    @Column(name = "localizacao", length = 45)
    private String localizacao;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusMaquina status;

    // getters e setters

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusMaquina getStatus() {
        return status;
    }

    public void setStatus(StatusMaquina status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "id_maquina: " + idMaquina +
                ", cod_patrimonial: " + codPatrimonial +
                ", num_serie: " + numSerie +
                ", valor: " + valor +
                ", localizacao: " + (localizacao != null ? localizacao : "null") +
                ", descricao: " + (descricao != null ? descricao : "null") +
                ", status: " + status +
                ", responsavel: " + (responsavel != null ? responsavel.getNomeFuncionario() : "null");
    }

}
