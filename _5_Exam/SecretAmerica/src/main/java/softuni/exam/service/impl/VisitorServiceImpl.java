package softuni.exam.service.impl;

import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VisitorImportXmlDto;
import softuni.exam.models.dto.VisitorRootDto;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Visitor;
import softuni.exam.repository.AttractionRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.PersonalDataRepository;
import softuni.exam.repository.VisitorRepository;
import softuni.exam.service.VisitorService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

//ToDo - Implement all the methods

@Service
public class VisitorServiceImpl implements VisitorService {

    private static final String FILE_PATH = "src/main/resources/files/xml/visitors.xml";

    private final VisitorRepository visitorRepository;
    private final PersonalDataRepository personalDataRepository;
    private final AttractionRepository attractionRepository;
    private final CountryRepository countryRepository;

    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public VisitorServiceImpl(VisitorRepository visitorRepository, PersonalDataRepository personalDataRepository, AttractionRepository attractionRepository, CountryRepository countryRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.visitorRepository = visitorRepository;
        this.personalDataRepository = personalDataRepository;
        this.attractionRepository = attractionRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean areImported() {
        return this.visitorRepository.count() > 0;
    }

    @Override
    public String readVisitorsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importVisitors() throws JAXBException {

        return "";
    }
}
