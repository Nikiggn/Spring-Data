package softuni.exam.models.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import softuni.exam.models.entity.PersonalData;

import java.util.List;

@XmlRootElement(name = "personal_datas") // Match the root element in the XML
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalDataRootDto {

    @XmlElement(name = "personal_data")
    private List<PersonalDataImportXmlDto> personalDataList;

    // Getter and setter
    public List<PersonalDataImportXmlDto> getPersonalDataList() {
        return personalDataList;
    }

    public void setPersonalDataList(List<PersonalDataImportXmlDto> personalDataList) {
        this.personalDataList = personalDataList;
    }
}