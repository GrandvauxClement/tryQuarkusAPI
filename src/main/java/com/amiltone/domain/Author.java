package com.amiltone.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
public class Author extends AbstractDomain{

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String name;

    @Email
    private String email;

    private Date birthDate;
}
