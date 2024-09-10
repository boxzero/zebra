package com.houseclay.zebra.dto.AddPropertyDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class AddPropertyDTO {

        private PropertyDetailsDTO propertyDetails;
        private LocalityDetailsDTO localityDetails;
        private OwnerDetailsDTO ownerDetails;
        private RentalDetailsDTO rentalDetails;
        private AmenitiesDTO amenities;
        private ScheduleDTO schedule;




}
