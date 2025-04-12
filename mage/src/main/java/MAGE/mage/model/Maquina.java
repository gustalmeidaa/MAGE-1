package MAGE.mage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Table(name = "Maquina")
public class Maquina {
    @Id
    @Column(name = "id_maquina")
    private Long idMaquina;
    @Column(name = "cod_patrimonial", length = 45)
    private String codPatrimonial;
    @Column(name = "num_serie")
    private String numSerie;
    @Column(name = "valor") // Podemos adicionar o número de casas decimais se necessário
    private BigDecimal valor;
    @Column(name = "id_responsavel")
    private int idResponsavel;
    @Column(name = "localizacao", length = 45)
    private String localizacao;

    public void agendarManutencoes(LocalDate dataManutencao){}
    public void registrarManutencao(){}
}
