package com.houseclay.zebra.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder
public class PropertySpecs {


    @OneToOne(targetEntity = Address.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id",referencedColumnName = "addr_id")
    private Address full_address;

    @Column(nullable = false)
    private String type;     // apartment , society , standalone

    private double build_up_area;


    @Column(nullable = false)
    private String beds;

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


    private String flooring; // null

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String description;


    @OneToOne(targetEntity = Amenities.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "amenitiesMap",referencedColumnName = "amenities_id")
    private Amenities amenitiesMap;

    private String latitude; // null

    private String longitude; // null

    private String locationurl; // null

//    -----------------------------------------------------------------

    @Column(nullable = false)
    private int totalFloors;

    @Column(nullable = false)
    private int floor;

}
