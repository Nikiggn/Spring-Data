package softuni.exam.models.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "visitors")
public class Visitor extends BaseEntity {
    // The combinations of first and last names must be unique!

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "attraction_id", referencedColumnName = "id", nullable = false)
    private Attraction attraction;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personalData_id", referencedColumnName = "id", nullable = false)
    private PersonalData personalData;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private Country country;


    public Visitor() {
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Visitor visitor = (Visitor) o;
        return Objects.equals(firstName, visitor.firstName) && Objects.equals(lastName, visitor.lastName) && Objects.equals(attraction, visitor.attraction) && Objects.equals(personalData, visitor.personalData) && Objects.equals(country, visitor.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, attraction, personalData, country);
    }
}
