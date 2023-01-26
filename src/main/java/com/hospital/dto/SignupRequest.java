package com.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignupRequest {
    @Size(min = 6, max = 20, message = "username is invalid")
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email(message = "email is invalid")
    private String email;
    private String firstName;
    private String lastName;
    @Size(min = 6, max = 50, message = "Password length is between 6 and 50")
    private String password;
    private Set<String> roles = new HashSet<>();

}
