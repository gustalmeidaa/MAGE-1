package MAGE.mage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "setor")
public class Setor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_setor")
    private Integer idSetor;

    @Column(name = "nome_setor", length = 45, nullable = false, unique = true)
    private String nomeSetor;

    public Integer getIdSetor() {
        return idSetor;
    }

    public void setIdSetor(Integer idSetor) {
        this.idSetor = idSetor;
    }

    public String getNomeSetor() {
        return nomeSetor;
    }

    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }

    @Override
    public String toString() {
        return "id_setor: " + idSetor + ", nome_setor: " + nomeSetor;
    }
}
