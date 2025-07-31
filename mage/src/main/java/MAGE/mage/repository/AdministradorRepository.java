package MAGE.mage.repository;

import MAGE.mage.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AdministradorRepository extends JpaRepository<Administrador, String> {
    UserDetails findByLogin(String login);
}
