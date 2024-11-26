package bg.softuni.productshop.service.impls;

import bg.softuni.productshop.data.entities.User;
import bg.softuni.productshop.data.repositories.UserRepository;
import bg.softuni.productshop.service.UserService;
import bg.softuni.productshop.service.dtos.UserSeedJsonDto;
import bg.softuni.productshop.utils.ValidatorUtilmpl;
import com.google.gson.Gson;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserServiceImpl implements UserService {
    private static final  String USERS_PATH = "src/main/resources/files/users.json";

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
}
