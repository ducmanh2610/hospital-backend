package com.hospital.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "toolKitType")
@Table(name = "tblToolkitType")
public class ToolkitType {
    @Id
    private String Id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "date_imported")
    private Date dateImported;
    @Column(name = "date_modified")
    private Date dateModified;
    @Column(name = "status")
    private boolean status;
    @Column(name = "image_url")
    private String imageURL;
    @OneToMany(mappedBy = "toolkitType", fetch = FetchType.LAZY)
    private List<Toolkit> toolkitList;
}
