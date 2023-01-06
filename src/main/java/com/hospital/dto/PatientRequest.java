package com.hospital.dto;

import com.hospital.entities.Records;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientRequest {
    @Id
    private String id;
    private String lastName;
    private String firstName;
    private String email;
    private Date dateOfBirth;
    private String address;
    private String phoneNumber;
    private Date dateJoined;
    private Date dateOut;
    private Date dateCreated;
    private boolean status;
    private Records records;
}
