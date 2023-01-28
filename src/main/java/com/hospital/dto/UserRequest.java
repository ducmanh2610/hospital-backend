package com.hospital.dto;

import com.hospital.entities.Employee;
import com.hospital.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRequest {
    @Id
    private String id;
    @NotNull
    @NotEmpty
    private String username;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;
    @NotNull
    @NotEmpty
    private String email;
    private boolean status;
    private Employee employee;
    private Set<Role> roles = new HashSet<>();
    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void setEmployee(Employee emp){
        this.employee = emp;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }
}
