package com.hospital.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Entity(name = "Employee")
@Table(name = "tblEmployee")
public class Employee {
    @Id
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private boolean status;
    @Column(name = "address")
    private String address;
    @Column(name = "date_imported")
    private Date dateImported;
    @Column(name = "date_modified")
    private Date dateModified;
    @JsonIgnore
    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "level_id")
    private Level level;
}
