package bg.softuni.productshop.service.impls;

import bg.softuni.productshop.data.entities.Product;
import bg.softuni.productshop.data.entities.User;
import bg.softuni.productshop.data.repositories.UserRepository;
import bg.softuni.productshop.service.UserService;
import bg.softuni.productshop.service.dtos.UserSeedJsonDto;
import bg.softuni.productshop.service.dtos.export.ProductSoldJsonDto;
import bg.softuni.productshop.service.dtos.export.UserSoldJsonDto;
import bg.softuni.productshop.utils.ValidatorUtilmpl;
import com.google.gson.Gson;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserServiceImpl implements UserService {
    private static final  String USERS_PATH = "src/main/resources/json/users.json";

    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidatorUtilmpl validatorUtil;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, Gson gson, ValidatorUtilmpl validatorUtil, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void seedUsers() throws IOException {
        String jsonString = String.join("", Files.readAllLines(Path.of(USERS_PATH)));
        UserSeedJsonDto[] userSeedJsonDto = gson.fromJson(jsonString, UserSeedJsonDto[].class);

        for (UserSeedJsonDto seedJsonDto : userSeedJsonDto) {
            if (!this.validatorUtil.isValid(seedJsonDto)){
                System.out.println(this.validatorUtil.validate(seedJsonDto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining()));

                continue;
            }

            this.userRepository.saveAndFlush(this.modelMapper.map(seedJsonDto, User.class));
        }
    }

    @Override
    public boolean areUsersImported() {
        return this.userRepository.count() > 0;
    }

    @Override
    public void successfullySoldProducts() throws IOException {
        Set<User> users = this.userRepository.findAllBySoldIsNotNull();
        Set<UserSoldJsonDto> userSoldJsonDtos = users.stream()
                .map(u -> {
                    UserSoldJsonDto userSoldJsonDto = this.modelMapper.map(u, UserSoldJsonDto.class);

                    if (u.getSold() != null) {
                        userSoldJsonDto.setSoldProducts(u.getSold()
                                .stream()
                                .map(p -> this.modelMapper.map(p, ProductSoldJsonDto.class))
                                .collect(Collectors.toSet()));
                    }

                    return userSoldJsonDto;
                })
                .collect(Collectors.toSet());


        String json = this.gson.toJson(userSoldJsonDtos);
        System.out.println(json);
        FileWriter fileWriter = new FileWriter("src/main/resources/json/exports/users-sold-products.json");
        fileWriter.write(json);
        fileWriter.close();

//        try (FileWriter fileWriter = new FileWriter("src/main/resources/json/exports/users-sold-products.json", false)) {
//            fileWriter.write(json);
//            fileWriter.flush(); // Ensure data is flushed to the file
//        }
    }
}


//public void successfullySoldProducts() throws IOException {
//    Set<User> users = this.userRepository.findAllBySoldIsNotNull();
//    Set<UserSoldJsonDto> userSoldJsonDtos = users.stream()
//            .filter(u -> u.getSold() != null &&u.getSold().stream().anyMatch(p -> p.getBuyer() != null))
//            .sorted(Comparator.comparing(User::getLastName).thenComparing(User::getFirstName))
//            .map(u -> {
//                UserSoldJsonDto userSoldJsonDto = this.modelMapper.map(u, UserSoldJsonDto.class);
//
//                // Map sold products to ProductSoldJsonDto, only those with a buyer
//                Set<ProductSoldJsonDto> productSoldJsonDtos = u.getSold().stream()
//                        .filter(p -> p.getBuyer() != null)  // Ensure the product has a buyer
//                        .map(p -> {
//                            ProductSoldJsonDto productDto = this.modelMapper.map(p, ProductSoldJsonDto.class);
//                            productDto.setBuyerFirstName(p.getBuyer().getFirstName());
//                            productDto.setBuyerLastName(p.getBuyer().getLastName());
//                            return productDto;
//                        })
//                        .collect(Collectors.toSet());
//
//                userSoldJsonDto.setSoldProducts(productSoldJsonDtos);
////                        .stream()
////                        .map(p -> this.modelMapper.map(p, ProductSoldJsonDto.class))
////                        .collect(Collectors.toSet()));
//                return userSoldJsonDto;
//            })
//            .collect(Collectors.toSet());
//
//
//    // Convert to JSON and write to file
//    String json = this.gson.toJson(userSoldJsonDtos);
//    try (FileWriter fileWriter = new FileWriter("src/main/resources/json/exports/users-sold-products.json")) {
//        fileWriter.write(json);
//    }