package com.hospital.dto;

import com.hospital.entities.Employee;
import com.hospital.entities.Roles;
import com.hospital.validations.PasswordMatches;
import com.hospital.validations.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@PasswordMatches
public class UserRequest {
    private String id;
    @NotNull
    @NotEmpty
    private String username;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    private String matchingPassword;
    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;
    private boolean status;
    private Date dateImported;
    private Date dateModified;
    private String roles;
    private Employee employee;

}
