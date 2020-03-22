package com.seller.panel.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Data
@Table(name = "companies")
public class Companies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Provide company name")
    private String name;

    @NotBlank(message = "Provide company code")
    private String code;

    private String description;

    private Boolean active = true;

    @Column(name = "created_on")
    private Instant createdOn = Instant.now();

    @Column(name = "updated_on")
    private Instant updatedOn = Instant.now();
}
