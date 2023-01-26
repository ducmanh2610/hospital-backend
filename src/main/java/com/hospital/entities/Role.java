package com.hospital.entities;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
@Entity(name = "Role")
@Table(name = "tblRoles")
public class Role {
    @Id
    private String id;
    @Column(length = 50, nullable = false, unique = true, name = "name")
    private String name;

    public Role(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
