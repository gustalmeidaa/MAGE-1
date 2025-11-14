package MAGE.mage.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historico_de_manutencoes")
public class Manutencao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historico_de_manutencoes")
    private Integer idHistoricoManutencoes;
    @Column(name = "data")
    private LocalDateTime data;
    @Column(name = "tipo_de_manutencao", length = 45)
    private String tipoManutencao;
    @Column(name = "procedimentos_realizados", length = 45)
    private String procedimentos;
    @ManyToOne
    @JoinColumn(name = "id_maquina")
    private Maquina idMaquina;
    @ManyToOne
    @JoinColumn(name = "id_funcionario")
    private Funcionario idFuncionario;

    public Integer getIdHistoricoManutencoes() {
        return idHistoricoManutencoes;
    }

    public void setIdHistoricoManutencoes(Integer idHistoricoManutencoes) {
        this.idHistoricoManutencoes = idHistoricoManutencoes;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
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

    public Maquina getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(Maquina idMaquina) {
        this.idMaquina = idMaquina;
    }

    public Funcionario getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Funcionario idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    @Override
    public String toString() {
        return "id_historico_de_manutencoes: " + idHistoricoManutencoes +
                ", data: " + data +
                ", tipo_de_manutencao: " + tipoManutencao +
                ", procedimentos_realizados: " + procedimentos +
                ", maquina: " + (idMaquina != null ? idMaquina.getCodPatrimonial() : "null") +
                ", funcionario: " + (idFuncionario != null ? idFuncionario.getNomeFuncionario() : "null");
    }

}