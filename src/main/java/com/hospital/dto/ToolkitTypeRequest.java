package com.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ToolkitTypeRequest {
    @Id
    private String Id;
    private String name;
    private String description;
    private Date dateModified;
    private Date dateCreated;
    private boolean status;
    private String imageURL;
}
