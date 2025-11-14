package MAGE.mage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log")
    private Integer idLog;

    @Column(name = "operacao", length = 45, nullable = false)
    private String operacao;

    @Column(name = "dados_antigos", columnDefinition = "TEXT", nullable = false)
    private String dadosAntigos;

    @Column(name = "dados_novos", columnDefinition = "TEXT", nullable = false)
    private String dadosNovos;

    @Column(name = "data_movimentacao", insertable = false, updatable = false)
    private java.sql.Timestamp dataMovimentacao;

    @Column(name = "login_usuario", length = 45, nullable = true)
    private String loginUsuario;

    // Construtores, Getters e Setters

    public Log() {
    }

    public Log(String operacao, String dadosAntigos, String dadosNovos, String loginUsuario) {
        this.operacao = operacao;
        this.dadosAntigos = dadosAntigos;
        this.dadosNovos = dadosNovos;
        this.loginUsuario = loginUsuario;
    }

    public Integer getIdLog() {
        return idLog;
    }

    public void setIdLog(Integer idLog) {
        this.idLog = idLog;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getDadosAntigos() {
        return dadosAntigos;
    }

    public void setDadosAntigos(String dadosAntigos) {
        this.dadosAntigos = dadosAntigos;
    }

    public String getDadosNovos() {
        return dadosNovos;
    }

    public void setDadosNovos(String dadosNovos) {
        this.dadosNovos = dadosNovos;
    }

    public java.sql.Timestamp getDataMovimentacao() {
        return dataMovimentacao;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }
}
