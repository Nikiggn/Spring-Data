package bg.softuni.gamestore.data.entities;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    public BaseEntity() {
    }

    public Long getId() {
        return id;
    }
}
