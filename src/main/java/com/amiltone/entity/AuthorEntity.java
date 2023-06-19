package com.amiltone.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorEntity extends AbstractEntity{
    private String firstName;

    private String name;

    private String email;

    private Date birthDate;


}
