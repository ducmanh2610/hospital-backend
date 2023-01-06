package com.hospital.dto;

import com.hospital.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LevelRequest {
    private String id;
    private String name;
    private boolean status;
    private String description;
    private Date dateImported;
    private Date dateModified;
    private List<Employee> employeeList;
}
