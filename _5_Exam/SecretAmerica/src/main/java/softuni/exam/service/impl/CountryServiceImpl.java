package softuni.exam.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryImportJsonDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

//ToDo - Implement all the methods
@Service
public class CountryServiceImpl implements CountryService {
    private static final String FILE_PATH = "src/main/resources/files/json/countries.json";

    private final CountryRepository countryRepository;

    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountryFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        String json = readCountryFileContent();
        CountryImportJsonDto[] dtos = gson.fromJson(json, CountryImportJsonDto[].class);

        StringBuilder sb = new StringBuilder();

        for (CountryImportJsonDto dto : dtos) {
            if (!this.validationUtil.isValid(dto) ||
                this.countryRepository.findByName(dto.getName()).isPresent()) {

                sb.append("Invalid country").append(System.lineSeparator());
                continue;
            }

            Country country = this.modelMapper.map(dto, Country.class);
            this.countryRepository.saveAndFlush(country);

            sb.append(String.format("Successfully imported country %s", country.getName())).append(System.lineSeparator());
        }

        return sb.toString();
    }
}
