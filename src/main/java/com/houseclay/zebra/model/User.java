package com.houseclay.zebra.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Collection;

@Entity
public class User {

    @Id private Long id;

    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private boolean enabled;
    private boolean tokenExpired;

    private Collection<Role> roles;


}
