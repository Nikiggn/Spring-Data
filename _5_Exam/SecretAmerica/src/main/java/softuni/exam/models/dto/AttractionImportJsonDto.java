package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

public class AttractionImportJsonDto {

    @Expose
    @Length(min = 5, max = 40)
    private String name;

    @Expose
    @Length(min = 10, max = 100)
    private String description;

    @Expose
    @Length(min = 3, max = 30)
    private String type;

    @Expose
    @Min(0)
    private int elevation;

    @Expose
    private int country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }
}
