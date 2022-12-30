package com.hospital.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "Roles")
@Table(name = "tblRoles")
public class Roles {
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
    @OneToOne(mappedBy = "roles", fetch = FetchType.LAZY)
    private User user;
}
