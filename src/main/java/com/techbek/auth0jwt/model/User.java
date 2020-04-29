package com.techbek.auth0jwt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    @NotNull
    @Column(unique = true)
    private String username;

    @NotBlank
    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @NotBlank
    @JsonIgnore
    private String password;

    @JsonIgnore
    private String resetEmailToken;

    @NotBlank
    private String firstName;

    @NotBlank
    private String secondName;

}
