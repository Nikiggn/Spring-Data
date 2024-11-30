package softuni.exam.service.impl;

import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PersonalDataImportXmlDto;
import softuni.exam.models.dto.PersonalDataRootDto;
import softuni.exam.models.entity.PersonalData;
import softuni.exam.models.entity.Visitor;
import softuni.exam.repository.PersonalDataRepository;
import softuni.exam.repository.VisitorRepository;
import softuni.exam.service.PersonalDataService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

//ToDo - Implement all the methods

@Service
public class PersonalDataServiceImpl implements PersonalDataService {
    private static final String FILE_PATH = "src/main/resources/files/xml/personal_data.xml";

    private final PersonalDataRepository personalDataRepository;
    private final VisitorRepository visitorRepository;

    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public PersonalDataServiceImpl(PersonalDataRepository personalDataRepository, VisitorRepository visitorRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.personalDataRepository = personalDataRepository;
        this.visitorRepository = visitorRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.personalDataRepository.count() > 0;
    }

    @Override
    public String readPersonalDataFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importPersonalData() throws IOException, JAXBException {
        StringBuilder stringBuilder = new StringBuilder();

        PersonalDataRootDto dtos = this.xmlParser.fromFile(FILE_PATH, PersonalDataRootDto.class);
        for (PersonalDataImportXmlDto personalDataImportXmlDto : dtos.getPersonalDataList()) {

            // First, retrieve the Optional<PersonalData> from the database
            Optional<PersonalData> personalDataOptional = this.personalDataRepository.findByCardNumber(personalDataImportXmlDto.getCardNumber());

            // Check if the PersonalData is invalid or already exists in the database
            if (!this.validationUtil.isValid(personalDataImportXmlDto) ||
                    personalDataOptional.isPresent() ||  // Check if the PersonalData already exists
                    !isValidBirthDate(personalDataImportXmlDto.getBirthDate())) {  // Check if the birth date is in the past
                stringBuilder.append("Invalid personal data").append(System.lineSeparator());
                continue;
            }

            // Now map the DTO to the entity
            PersonalData personalData = this.modelMapper.map(personalDataImportXmlDto, PersonalData.class);

            // Save the PersonalData
            this.personalDataRepository.saveAndFlush(personalData);

            stringBuilder.append(String.format("Successfully imported personal data for visitor with card number %s", personalData.getCardNumber())).append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }

    // Helper method to validate the birth date
    private boolean isValidBirthDate(String birthDate) {
        try {
            // Parse the birth date string to a LocalDate object
            LocalDate parsedDate = LocalDate.parse(birthDate, DateTimeFormatter.ISO_DATE);

            // Check if the birth date is before the current date
            return parsedDate.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            // If the date format is invalid, return false
            return false;
        }
    }
}
