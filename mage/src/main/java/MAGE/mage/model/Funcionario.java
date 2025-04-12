package MAGE.mage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Funcionario")
public class Funcionario {
    @Id
    @Column(name = "id_funcionario")
    private Integer id;

    @Column(name = "nome_funcionario", length = 45, nullable = false)
    private String nome;

    @Column(name = "id_cargo")
    private Integer cargo;
}
