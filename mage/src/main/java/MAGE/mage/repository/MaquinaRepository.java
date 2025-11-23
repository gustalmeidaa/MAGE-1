package MAGE.mage.repository;

import MAGE.mage.model.Maquina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MaquinaRepository extends JpaRepository<Maquina, Integer> {

    @Query("SELECT COUNT(m) FROM Maquina m WHERE m.status = 'Ativa'")
    long countAtivas();

    @Query("SELECT COUNT(m) FROM Maquina m WHERE m.status = 'Inativa'")
    long countInativas();

    @Query("SELECT COUNT(m) FROM Maquina m WHERE m.status = 'Em_manutencao'")
    long countEmManutencao();

    List<Maquina> findByCodPatrimonial(String codPatrimonial);
    List<Maquina> findByNumSerie(String numSerie);
}
