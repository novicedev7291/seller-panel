package com.seller.panel.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(name = "company_id")
    private String companyId;

    @Column(name = "is_deletable")
    private Boolean isDeletable = true;

    private Boolean active = true;

    @Column(name = "created_on")
    private Instant createdOn = Instant.now();

    @Column(name = "updated_on")
    private Instant updatedOn = Instant.now();

}
