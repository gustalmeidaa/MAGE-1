package MAGE.mage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "registro_de_movimentacoes")
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro_de_movimentacoes")
    private Integer idMovimentacoes;
    @Column(name = "id_responsavel")
    private Integer idFuncionario;

    @Column(name = "tipo_de_movimentacao", length = 45)
    private String tipo;

    @Column(name = "origem", length = 45)
    private String origem;
    @Column(name = "Destino", length = 45)
    private String destino;

    public Integer getIdMovimentacoes() {
        return idMovimentacoes;
    }

    public void setIdMovimentacoes(Integer idMovimentacoes) {
        this.idMovimentacoes = idMovimentacoes;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
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
}
