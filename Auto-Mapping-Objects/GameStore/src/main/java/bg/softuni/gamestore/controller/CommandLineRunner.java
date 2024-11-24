package bg.softuni.gamestore.controller;

import bg.softuni.gamestore.service.UserService;
import bg.softuni.gamestore.service.dtos.UserCreateDto;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

    private final UserService userService;

    public CommandLineRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line;
        String command = "";

        while (!(line =  reader.readLine()).equals("stop") ) {


            String[] tokens = line.split("\\|");

            switch (tokens[0]){
                case "RegisterUser":
                    command = this.userService.registerUser(new UserCreateDto(tokens[1], tokens[2], tokens[3], tokens[4]));
                    break;
            }

            System.out.println(command);

        }
    }
}
