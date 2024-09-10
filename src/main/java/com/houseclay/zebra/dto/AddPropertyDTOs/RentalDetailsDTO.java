package com.houseclay.zebra.dto.AddPropertyDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class RentalDetailsDTO {

        private String propertyAvailable;
        private double expectedRent;
        private double expectedDeposit;
        private String monthlyMaintenance;
        private Date availableFrom;
        private String furniture;
        private String parking;
        private String description;
        private List<String> preferredTenants;
        private double expectedMaintenance;
        private boolean rentNegotiable;



}
