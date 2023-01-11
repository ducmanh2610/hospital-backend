package com.hospital.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "User")
@Table(name = "tblUser", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    private String id;
    @Column(name = "username")
    @Size(max = 20)
    private String username;
    @Column(name = "password")
    @Size(max = 50)
    private String password;
    @Column(name = "email")
    @NotBlank
    @Size(max = 50)
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
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tblUserRole",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles = new HashSet<>();
    @Column(name = "enabled")
    private boolean enabled;

    public User(String username, String email, String password, Set<Roles> roles){
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
