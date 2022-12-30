package com.hospital.dto;

import com.hospital.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LevelResponse {
    private String id;
    private String name;
    private boolean status;
    private Date dateImported;
    private Date dateModified;
    private List<Employee> employeeList;
}
