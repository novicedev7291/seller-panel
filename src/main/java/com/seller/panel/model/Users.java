package com.seller.panel.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String phone;

    @Column(length = 60)
    private String password;

    private Boolean active = true;

    @Column(name = "company_id")
    private String companyId;

    @Column(name = "created_on")
    private Instant createdOn = Instant.now();

    @Column(name = "updated_on")
    private Instant updatedOn = Instant.now();

}
