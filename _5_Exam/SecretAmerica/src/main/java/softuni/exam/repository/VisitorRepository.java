package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.PersonalData;
import softuni.exam.models.entity.Visitor;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface VisitorRepository  extends JpaRepository<Visitor, Long> {
    Optional<Visitor> findByPersonalData(PersonalData personalData);

    Optional<Visitor> findByFirstName(String firstName);

    Optional<Visitor> findByFirstNameAndLastName(String firstName, String lastName);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    Optional<Visitor> findByLastName(String firstName);
}
