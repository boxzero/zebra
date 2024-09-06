package com.houseclay.zebra.model.rental;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.houseclay.zebra.model.common.BaseTimeStamp;
import com.houseclay.zebra.model.common.PropertySpecs;
import com.houseclay.zebra.model.tenant.Tenant;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
@Table(name = "properties_for_rent")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertyRent {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID" , strategy = "org.hibernate.id.UUIDGenerator")
    private UUID property_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String title;

    @OneToOne(targetEntity = Owner.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_reference_id",referencedColumnName = "owner_id")
    private Owner owner;

    private boolean isManaged; //false by default

    @Embedded
    PropertySpecs propertySpecs;

    @OneToMany(targetEntity = Images.class,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "mapped_property",referencedColumnName = "property_id")
    private List<Images> imageMap;

    @Column(nullable = false)
    private boolean active_status; // false by default

    private String inactive_reason; // null

    @Column(nullable = false)
    private Date posted_on;


    @Column(nullable = false)
    private Date available_from;

    @Column(nullable = false)
    private double property_rent;

    @Column(nullable = false)
    private double property_maintenance;


    @ElementCollection
    @CollectionTable(name = "preferred_tenant_types", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "tenant_type")
    private List<String> preferred_tenant_type;



    @Embedded
    private BaseTimeStamp baseTimeStamp;


//---------------------------------------
    private String folderUrl;
    private String folderName;


    private String propertyFor;
    private Double  expected_Deposit;
    private boolean rent_negotiable;

    private String who_will_show_the_property;
    private String showProperty_contact;

    private String availability;
    private String startTime;
    private String endTime;

    @ElementCollection
    @CollectionTable(name="image_urls", joinColumns = @JoinColumn(name="property_id"))
    @Column(name = "image_url")
    private List<String> imagesUrls;


}
