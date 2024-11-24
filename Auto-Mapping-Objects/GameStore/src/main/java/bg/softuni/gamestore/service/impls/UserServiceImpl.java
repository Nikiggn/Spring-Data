package bg.softuni.gamestore.service.impls;

import bg.softuni.gamestore.data.entities.User;
import bg.softuni.gamestore.data.repositories.UserRepository;
import bg.softuni.gamestore.service.UserService;
import bg.softuni.gamestore.service.dtos.UserCreateDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String registerUser(UserCreateDto userCreateDto) {
        if (!userCreateDto.getPassword().equals(userCreateDto.getConfirmPassword())) {

            return "Passwords do not match";
        }

        User user = this.modelMapper.map(userCreateDto, User.class);
        setRootUserAdmin(user);
        this.userRepository.saveAndFlush(user);
        return String.format("%s was registered%n", user.getFullName());
    }

    private void setRootUserAdmin(User user) {
        if (this.userRepository.count() == 0){
            user.setAdministrator(true);
        }
    }
}
