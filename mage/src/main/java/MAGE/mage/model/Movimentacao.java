package MAGE.mage.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "registro_de_movimentacoes")
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro_de_movimentacoes")
    private Integer idMovimentacoes;

    @ManyToOne
    @JoinColumn(name = "id_maquina_movimentada")
    private Maquina maquinaMovimentada;

    @Column(name = "tipo_de_movimentacao", length = 45)
    private String tipo;

    @Column(name = "origem", length = 45)
    private String origem;
    @Column(name = "destino", length = 45)
    private String destino;
    @Column(name = "data")
    private LocalDateTime data;

    // getters e setters


    public Integer getIdMovimentacoes() {
        return idMovimentacoes;
    }

    public void setIdMovimentacoes(Integer idMovimentacoes) {
        this.idMovimentacoes = idMovimentacoes;
    }

    public Maquina getMaquinaMovimentada() {
        return maquinaMovimentada;
    }

    public void setMaquinaMovimentada(Maquina maquinaMovimentada) {
        this.maquinaMovimentada = maquinaMovimentada;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "id_registro_de_movimentacoes: " + idMovimentacoes +
                ", maquina_movimentada: " + (maquinaMovimentada != null ? maquinaMovimentada.getCodPatrimonial() : "null") +
                ", tipo_de_movimentacao: " + tipo +
                ", origem: " + origem +
                ", destino: " + destino +
                ", data: " + data;
    }

}
