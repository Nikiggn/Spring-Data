package bg.softuni.gamestore.service;

import bg.softuni.gamestore.service.dtos.UserCreateDto;

public interface UserService {

    String registerUser(UserCreateDto userCreateDto);
}
