package com.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequest {
    @Id
    private Long id;
    private String name;
    private boolean status;
    private String description;
    private String imageUrl;
}