package com.houseclay.zebra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "properties_for_rent")
public class PropertyRent {

    @Id private long property_id;

    private String name;

    private String title;

    private String full_address;

    @ManyToOne
    private Owner owner;

    private boolean isManaged;

    private String type;

    private double super_area;

    private double carpet_area;

    private int beds;

    private int bath;

    private int balcony;

    private String parking ; //Car and Bike , Only Car

    private String facing ;

    private String property_age ;

    private String furnishing;

    private String flooring;

    private List<Images> imageMap;

    private boolean active_status;

    private String inactive_reason;

    private Date posted_on;

    private Date available_from;

    private double property_rent;

    private double property_maintenance;

    private String city;

    private String description;

    @OneToOne
    @JoinColumn(name = "id")
    private Amenities amenitiesMap;

    private String latitude;

    private String longitude;

    private String locationurl;

    private String preferred_tenant_type;

    private String created_by;

    private Date created_on;

    private String changed_by;

    private Date changed_on;



}
