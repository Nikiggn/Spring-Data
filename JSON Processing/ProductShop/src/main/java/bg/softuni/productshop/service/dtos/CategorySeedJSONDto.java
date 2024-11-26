package bg.softuni.productshop.service.dtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

public class CategorySeedJSONDto {

    @Expose
    @Length(min = 3, max = 15)
    private String name;

    public CategorySeedJSONDto() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
