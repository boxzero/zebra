package com.houseclay.zebra.dto;

import com.houseclay.zebra.model.lead.Lead;
import com.houseclay.zebra.model.lead.enums.LeadStatus;
import com.houseclay.zebra.model.lead.enums.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import java.util.ArrayList;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class NewLeadTenantDTO {

    @Embedded
    private Lead lead;

    private Long minBudget;
    private Long maxBudget;

    private PropertyType propertyType;

    private LeadStatus leadStatus;

    private ArrayList<String> preferredLocations;

    private ArrayList<String> assetConfigurations;

    private Date occupancyDate;

    private Boolean isDateFlexible;

    private String tenantType ; // family, bachelors, company

    private Boolean isVegetarian ;

    private Boolean isHavingPets ;

    private Boolean isLookingForARoom ;
}
