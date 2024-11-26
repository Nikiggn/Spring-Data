package bg.softuni.productshop.data.repositories;

import bg.softuni.productshop.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
