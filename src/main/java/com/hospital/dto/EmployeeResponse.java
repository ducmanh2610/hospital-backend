package com.hospital.dto;

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
public class EmployeeResponse {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String description;
    private boolean status;
    private String address;
    private Date dateImported;
    private Date dateModified;
    private User user;
    private LevelResponse level;
}
