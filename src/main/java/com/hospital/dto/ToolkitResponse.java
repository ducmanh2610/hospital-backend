package com.hospital.dto;

import com.hospital.entities.ToolkitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToolkitResponse {
    @Id
    private String Id;
    private String name;
    private String description;
    private Date dateImported;
    private Date dateModified;
    private boolean status;
    private String imageURL;
    private ToolkitType toolkitType;
}
