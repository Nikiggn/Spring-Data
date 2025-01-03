package bg.softuni.productshop.data.repositories;

import bg.softuni.productshop.data.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c ORDER BY RAND() LIMIT : count")
    Set<Category> findRandomCategories(int count);
}
