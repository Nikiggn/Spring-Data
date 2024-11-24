package bg.softuni.gamestore.service.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class UserCreateDto {

    @Pattern(regexp = "^((\\w+)|(\\w+.\\w+))@\\w+\\.\\w+$", message = "Invalid email")
    private String email;

    @Pattern(regexp = "^$", message = "Invalid password")
    private String password;

    @Pattern(regexp = "", message = "Invalid confirmPassword")
    private String confirmPassword;
    private String fullName;

    public UserCreateDto() {
    }

    public UserCreateDto(String email, String password, String confirmPassword, String fullName) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
