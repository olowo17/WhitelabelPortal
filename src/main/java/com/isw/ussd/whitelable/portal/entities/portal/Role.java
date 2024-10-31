package com.isw.ussd.whitelable.portal.entities.portal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Builder
@Table(name = "Roles")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "institution_id")
    Long institutionId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "authorizer")
    private boolean authorizer;


    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date dateCreated;

    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    @Column(name = "date_updated")
    private Date dateUpdated;
}
