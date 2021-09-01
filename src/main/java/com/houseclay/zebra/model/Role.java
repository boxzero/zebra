package com.houseclay.zebra.model;



import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Collection;

@Entity
public class Role {

    @Id private Long id;

    private String name;

    private Collection<User> users;

    private Collection<Privilege> privileges;
}
