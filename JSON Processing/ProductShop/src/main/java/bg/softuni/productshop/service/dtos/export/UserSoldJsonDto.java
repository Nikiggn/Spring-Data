package bg.softuni.productshop.service.dtos.export;

import bg.softuni.productshop.data.entities.Product;
import com.google.gson.annotations.Expose;

import java.util.Set;

public class UserSoldJsonDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Set<ProductSoldJsonDto> soldProducts;

    public UserSoldJsonDto() {
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

    public Set<ProductSoldJsonDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(Set<ProductSoldJsonDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
