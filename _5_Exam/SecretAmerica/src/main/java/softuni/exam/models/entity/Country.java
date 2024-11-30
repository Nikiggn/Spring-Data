package softuni.exam.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity {

    @Column
    private double area;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "country")
    private Set<Attraction> attractions;

    @OneToMany(mappedBy = "country")
    private Set<Visitor> visitors;

    public Country() {
    }

    public Set<Visitor> getVisitors() {
        return visitors;
    }

    public void setVisitors(Set<Visitor> visitors) {
        this.visitors = visitors;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(Set<Attraction> attractions) {
        this.attractions = attractions;
    }
}
