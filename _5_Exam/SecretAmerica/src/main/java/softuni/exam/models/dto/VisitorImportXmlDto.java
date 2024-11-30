package softuni.exam.models.dto;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.Length;

@XmlRootElement(name = "visitor")
@XmlAccessorType(XmlAccessType.FIELD)
public class VisitorImportXmlDto {

    @XmlElement(name = "first_name")
    @Length(min = 2 , max = 20)
    private String firstName;

    @XmlElement(name = "last_name")
    @Length(min = 2 , max = 20)
    private String lastName;

    @XmlElement(name = "attraction_id")
    private int attractionId;

    @XmlElement(name = "country_id")
    private int countryId;

    @XmlElement(name = "personal_data_id")
    private int personalDataId;

    // Getters and setters (or Lombok annotations)
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

    public int getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getPersonalDataId() {
        return personalDataId;
    }

    public void setPersonalDataId(int personalDataId) {
        this.personalDataId = personalDataId;
    }
}
