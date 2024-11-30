package softuni.exam.models.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "visitors") // This matches the root element in the XML
@XmlAccessorType(XmlAccessType.FIELD)
public class VisitorRootDto {

    @XmlElement(name = "visitor") // This matches each visitor element inside the root
    private List<VisitorImportXmlDto> visitorList;

    // Getter and setter
    public List<VisitorImportXmlDto> getVisitorList() {
        return visitorList;
    }

    public void setVisitorList(List<VisitorImportXmlDto> visitorList) {
        this.visitorList = visitorList;
    }
}
