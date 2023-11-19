package Banco.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import Banco.main.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
UserDetails findByLogin(Object login);
}
