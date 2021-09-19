package com.houseclay.zebra.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "properties_for_rent")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertyRent {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID" , strategy = "org.hibernate.id.UUIDGenerator")
    private UUID property_id;


    @Column(nullable = false)
    private String name;  //name of the society or apartment or the building


    @Column(nullable = false)
    private String title;


    @OneToOne(targetEntity = Address.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id",referencedColumnName = "addr_id")
    private Address full_address;


    @OneToOne(targetEntity = Owner.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_reference_id",referencedColumnName = "owner_id")
    private Owner owner;


    private boolean isManaged;

    @Column(nullable = false)
    private String type;     // apartment , society , standalone

    private double super_area;

    private double carpet_area;

    @Column(nullable = false)
    private int beds;

    @Column(nullable = false)
    private int bath;

    @Column(nullable = false)
    private int balcony;

    @Column(nullable = false)
    private String parking ; //Car and Bike , Only Car


    private String facing ;

    @Column(nullable = false)
    private String property_age ;

    @Column(nullable = false)
    private String furnishing;


    private String flooring;


    @OneToMany(targetEntity = Images.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "imageMap",referencedColumnName = "property_id")
    private List<Images> imageMap;

    @Column(nullable = false)
    private boolean active_status;


    private String inactive_reason;

    @Column(nullable = false)
    private Date posted_on;

    @Column(nullable = false)
    private Date available_from;

    @Column(nullable = false)
    private double property_rent;

    @Column(nullable = false)
    private double property_maintenance;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String description;


    @OneToOne(targetEntity = Amenities.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "amenitiesMap",referencedColumnName = "amenities_id")
    private Amenities amenitiesMap;

    private String latitude;

    private String longitude;

    private String locationurl;

    private String preferred_tenant_type;

    @Column(nullable = false)
    private String created_by;

    @Column(nullable = false)
    private Date created_on;


    private String changed_by;


    private Date changed_on;



}
