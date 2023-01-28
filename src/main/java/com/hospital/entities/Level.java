package com.hospital.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tblLevel")
//@JsonIgnoreProperties({"hibernateLazyInitializer"})
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
}
