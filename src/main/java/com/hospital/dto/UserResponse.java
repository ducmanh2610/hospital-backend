package com.hospital.dto;

import com.hospital.entities.Employee;
import com.hospital.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponse {
    private String id;
    private String username;
    private String password;
    private boolean status;
    private Date dateImported;
    private Date dateModified;
    private Employee employee;

    private Role roles;
}
