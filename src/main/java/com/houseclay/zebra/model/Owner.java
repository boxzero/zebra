package com.houseclay.zebra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "owner")
public class Owner {
    @Id
    @Column(name="owner_id")
    private long owner_id;
    @Column(name="owner_name")
    private String owner_name;

    private String owner_email;

    private String owner_contact;

    private String owner_pan;

    private String owner_contact_addr;

    private String notes;




}
