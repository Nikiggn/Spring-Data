package bg.softuni.gamestore.data.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column
    private String email;

    @Column
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @ManyToMany
    @JoinTable(name = "users_games"
    , joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    , inverseJoinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"))
    private Set<Game> games;

    @Column(name = "is_administrator")
    private boolean isAdministrator;

    public User() {
    }

    public User(String email, String password, String fullName, Set<Game> games, boolean isAdministrator) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.games = games;
        this.isAdministrator = isAdministrator;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }
}
