package com.hospital.dto;

import com.hospital.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LevelRequest {
    private String id;
    private String name;
    private boolean status;
    private String description;
}
