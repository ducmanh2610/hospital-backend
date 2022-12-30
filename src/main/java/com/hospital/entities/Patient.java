package com.hospital.entities;

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
@Entity(name = "Patient")
@Table(name = "tblPatient")
public class Patient {
    @Id
    private String id;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "email")
    private String email;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "address")
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "date_joined")
    private Date dateJoined;
    @Column(name = "dateOut")
    private Date dateOut;
    @Column(name = "date_created")
    private Date dateCreated;
    @Column(name = "status")
    private boolean status;
    @OneToOne(mappedBy = "patient")
    private Records records;
}
