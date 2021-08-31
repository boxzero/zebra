package com.houseclay.zebra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tenant")
public class Tenant {

    @Id
    private long tenant_id;

    private String tenant_name;

    private String tenant_email;

    private String tenant_contact;

    private String tenant_type;

    private String tenant_contact_addr;

    private String notes;



}
