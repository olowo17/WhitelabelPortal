package com.isw.ussd.whitelable.portal.entities.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "properties")
public class InstitutionProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "application")
    private String application;

    @ManyToOne(targetEntity = Institution.class)
    @JoinColumn(name = "profile_id", nullable = false)
    private Institution profile;

    @Column(name = "property_key")
    private String key;

    @Column(name = "value", length = 1024)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id", nullable = false)
    private Institution institution;
}
