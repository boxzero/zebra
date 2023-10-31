package com.houseclay.zebra.model.lead;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.houseclay.zebra.model.lead.enums.LeadStatus;
import com.houseclay.zebra.model.lead.enums.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "leads_owner")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeadOwner {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID _owner_lead_id;

    @Embedded private Lead lead;

    /* Start - Property Specs */
    private PropertyType propertyType;

    private String bhkType;

    private String apartmentName; //society Name

    private String locality;

    private String googleMapLocationURL;
    /* End - Property Specs */

    private LeadStatus leadStatus;

    private String preferredTenants; // Bachelors , Family , Open to Both , Company

    private boolean isNonVegAllowed;

    private Date availableFrom ;

    private Long expectedRent;

    private Long expectedDeposit;


}
