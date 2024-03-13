package pl.korpetycje.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.korpetycje.demo.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    User findById(long id);
}
