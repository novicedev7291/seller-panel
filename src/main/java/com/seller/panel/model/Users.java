package com.seller.panel.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private Long companyId;

    @Column(name = "created_on")
    private Instant createdOn = Instant.now();

    @Column(name = "updated_on")
    private Instant updatedOn = Instant.now();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Roles> roles;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="company_id", insertable=false, updatable=false)
    private Companies company;

    public String getFullName() {
        return StringUtils.isNotBlank(lastName) ? firstName + " " + lastName : firstName;
    }

}
