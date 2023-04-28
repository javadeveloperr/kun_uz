package com.example.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {
    @NotBlank(message = "Field must have some value")
    private String name;
    @NotBlank(message = "Field must have some value")
    private String surname;
    @Email(message = "Email not valid")
    private String email;
    @NotBlank(message = "Field must have some value")
    private String phone;
    @NotNull(message = "Field must have some value")
    private String password;
}
