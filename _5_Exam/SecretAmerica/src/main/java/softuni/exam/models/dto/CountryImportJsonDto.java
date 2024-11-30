package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

public class CountryImportJsonDto {
    @Expose
    @Length(min = 3, max = 40)
    private String name;

    @Expose
    @Min(1)
    private double area;


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public double getArea() {
        return area;
    }


    public void setArea(double area) {
        this.area = area;
    }


}
