package com.hospital.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "User")
@Table(name = "tblUser")
public class User {
    @Id
    private String id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "status")
    private boolean status;
    @Column(name = "date_imported")
    private Date dateImported;
    @Column(name = "date_modified")
    private Date dateModified;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "employee_id")
    private Employee employee;
    @Column(name = "roles")
    private String roles;
    @Column(name = "enabled")
    private boolean enabled;
}
