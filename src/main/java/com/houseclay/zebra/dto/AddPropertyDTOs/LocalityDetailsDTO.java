package com.houseclay.zebra.dto.AddPropertyDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class LocalityDetailsDTO {
        private String city;
        private String locality;
        private String landmark;
        private String houseNumber;



}
