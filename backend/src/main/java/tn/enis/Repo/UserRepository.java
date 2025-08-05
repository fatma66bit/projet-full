package tn.enis.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.enis.Entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFullNameAndPassword(String fullName, String password);
}
