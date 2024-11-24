package bg.softuni.gamestore.data.repositories;

import bg.softuni.gamestore.data.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
