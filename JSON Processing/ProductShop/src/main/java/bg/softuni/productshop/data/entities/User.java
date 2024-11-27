package bg.softuni.productshop.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {


    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column
    private int age;

    @ManyToMany(fetch =  FetchType.EAGER)
    @JoinTable(name = "users_friends",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    private Set<User> friends;

    @OneToMany(mappedBy = "seller",fetch = FetchType.EAGER)
    private Set<Product> sold;

    public User() {
    }

    public User(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.friends = new HashSet<>();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public Set<Product> getSold() {
        return sold;
    }

    public void setSold(Set<Product> sold) {
        this.sold = sold;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (o == null || getClass() != o.getClass()) return false;
//        User user = (User) o;
//        return age == user.age && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(friends, user.friends) && Objects.equals(sold, user.sold);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(firstName, lastName, age, friends, sold);
//    }
}
