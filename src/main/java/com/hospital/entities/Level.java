package com.hospital.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "Level")
@Table(name = "tblLevel")
public class Level {
    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "status")
    private boolean status;
    @Column(name = "date_imported", updatable = false)
    private Date dateImported;
    @Column(name = "date_modified")
    private Date dateModified;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "level")
    private List<Employee> employeeList;
}
