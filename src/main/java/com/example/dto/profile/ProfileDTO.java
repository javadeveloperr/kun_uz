package com.example.dto.profile;

import com.example.enums.ProfileRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO {
    private Integer id;
    @NotNull(message = "name required")
    private String name;
    @NotNull(message = "surname required")
    private String surname;
    @Email(message = "message not valid")
    private String email;
    @NotNull(message = "phone required")
    private String phone;
    @NotNull(message = "password required")
    private String password;
    private ProfileRole role;
}
