package com.hospital.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "toolKit")
@Table(name = "tblToolkit")
public class Toolkit {
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
    @Column(name = "image_URL")
    private String imageURL;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toolkit_type_id", referencedColumnName = "id", nullable = false)
    private ToolkitType toolkitType;
}
