package com.hospital.dto;

import com.hospital.entities.ToolkitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ToolkitRequest {
    @Id
    private String Id;
    private String name;
    private String description;
    private String imageURL;
    private ToolkitType toolkitType;

}
