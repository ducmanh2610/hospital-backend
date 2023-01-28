package com.hospital.dto;

import com.hospital.entities.Department;
import com.hospital.entities.Level;
import com.hospital.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequest {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String description;
    private String email;
    private boolean status;
    private String address;
    private Date dateModified;
    private Date dateImported;
    private User user;
    private Level level;
    private Department department;
}
