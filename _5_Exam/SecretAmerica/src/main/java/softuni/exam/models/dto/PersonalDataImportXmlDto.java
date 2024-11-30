package softuni.exam.models.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "personal_data")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalDataImportXmlDto {

    @XmlElement
    @Min(1)
    @Max(100)
    private int age;  // Integer for nullable validation

    @XmlElement
    @Pattern(regexp = "^[MF]$")  // Only "M" or "F"
    private String gender;  // Nullable

    @XmlElement(name = "birth_date")
    private String birthDate;  // Nullable

    @XmlElement(name = "card_number")
    @Pattern(regexp = "^[0-9]{9}$")  // 9 digits only
    private String cardNumber;  // Not nullable, unique in the DB

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
