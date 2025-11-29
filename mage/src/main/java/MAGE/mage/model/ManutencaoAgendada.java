package MAGE.mage.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "manutencoes_agendadas")
public class ManutencaoAgendada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_manutencao_agendada")
    private Integer idManutencaoAgendada;

    @Column(name = "data_agendada", nullable = false)
    private LocalDateTime dataAgendada;

    @Column(name = "tipo_de_manutencao", length = 45, nullable = false)
    private String tipoManutencao;

    @Column(name = "procedimentos", length = 255, nullable = false)
    private String procedimentos;

    @Column(name = "custo_manutencao", precision = 10, scale = 2)
    private BigDecimal custoManutencao;  // Novo campo para custo

    @ManyToOne
    @JoinColumn(name = "id_maquina")
    private Maquina maquina;

    // Getters e Setters

    public Integer getIdManutencaoAgendada() {
        return idManutencaoAgendada;
    }

    public void setIdManutencaoAgendada(Integer idManutencaoAgendada) {
        this.idManutencaoAgendada = idManutencaoAgendada;
    }

    public LocalDateTime getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(LocalDateTime dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public String getTipoManutencao() {
        return tipoManutencao;
    }

    public void setTipoManutencao(String tipoManutencao) {
        this.tipoManutencao = tipoManutencao;
    }

    public String getProcedimentos() {
        return procedimentos;
    }

    public void setProcedimentos(String procedimentos) {
        this.procedimentos = procedimentos;
    }

    public BigDecimal getCustoManutencao() {
        return custoManutencao;
    }

    public void setCustoManutencao(BigDecimal custoManutencao) {
        this.custoManutencao = custoManutencao;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    @Override
    public String toString() {
        return "id_manutencao_agendada: " + idManutencaoAgendada +
                ", data_agendada: " + dataAgendada +
                ", tipo_de_manutencao: " + tipoManutencao +
                ", procedimentos: " + procedimentos +
                ", custo_manutencao: " + custoManutencao +
                ", maquina: " + (maquina != null ? maquina.getCodPatrimonial() : "null");
    }
}
