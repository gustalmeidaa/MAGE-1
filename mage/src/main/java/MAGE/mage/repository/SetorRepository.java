package MAGE.mage.repository;

import MAGE.mage.model.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Integer> {
    Optional<Setor> findByNomeSetor(String nomeSetor);
}
