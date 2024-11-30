package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AttractionImportJsonDto;
import softuni.exam.models.entity.Attraction;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.AttractionRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.AttractionService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

//ToDo - Implement all the methods
@Service

public class AttractionServiceImpl implements AttractionService {
    private static final String FILE_PATH = "src/main/resources/files/json/attractions.json";

    private final AttractionRepository attractionRepository;
    private final CountryRepository countryRepository;

    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;


    @Autowired
    public AttractionServiceImpl(AttractionRepository attractionRepository, CountryRepository countryRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.attractionRepository = attractionRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.attractionRepository.count() > 0;
    }

    @Override
    public String readAttractionsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importAttractions() throws IOException {
        String json = readAttractionsFileContent();
        AttractionImportJsonDto[] dtos = gson.fromJson(json, AttractionImportJsonDto[].class);

        StringBuilder sb = new StringBuilder();



        for (AttractionImportJsonDto dto : dtos) {
            if (!this.validationUtil.isValid(dto) ||
                    this.attractionRepository.findByName(dto.getName()).isPresent() ||
                    this.countryRepository.findById((long) dto.getCountry()).isEmpty()) {

                sb.append("Invalid attraction").append(System.lineSeparator());
                continue;
            }
            Country country = this.countryRepository.findById((long) dto.getCountry()).get();
            Attraction attraction = this.modelMapper.map(dto, Attraction.class);

            attraction.setCountry(country);
            this.attractionRepository.saveAndFlush(attraction);

            sb.append(String.format("Successfully imported attraction %s", dto.getName())).append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public String exportAttractions() {
        StringBuilder sb = new StringBuilder();

        // Fetching the filtered and sorted list of attractions
        List<Attraction> attractions = attractionRepository.findHistoricalOrArchaeologicalAttractionsWithHighElevation();

        // Formatting the output
        for (Attraction attraction : attractions) {
            sb.append(String.format("Attraction with ID%d:\n" +
                    "***%s - %s at an altitude of %dm. somewhere in %s.\n",
                    attraction.getId(), attraction.getName(), attraction.getDescription(), attraction.getElevation(),attraction.getCountry().getName()));
        }

        return sb.toString();
    }
}
