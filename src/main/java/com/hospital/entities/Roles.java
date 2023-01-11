package com.hospital.entities;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tblRoles")
public class Roles {
    @Id
    private String id = UUID.randomUUID().toString();
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
}
