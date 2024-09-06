package com.houseclay.zebra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LocationDTO {

    private String city;
    private String locationName;
    private Long pincode;
    private String state;
}
