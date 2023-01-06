package com.hospital.dto;

import com.hospital.entities.Employee;
import com.hospital.entities.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RecordsRequest {
    @Id
    private String id;
    private String title;
    private Employee employee;
    private Patient patient;
    private boolean status;
}
