package com.houseclay.zebra.dto.AddPropertyDTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder(toBuilder = true)
public class AmenitiesDTO {

        private int bathroom;
        private int balcony;
        private String waterSupply;
        private String gym;
        private String nonVegAllowed;
        private String gatedSecurity;
        private String showProperty;
        private String description;
        private String contactNumber;
        private Map<String, Boolean> amenities; // Nested amenities as a map


}
