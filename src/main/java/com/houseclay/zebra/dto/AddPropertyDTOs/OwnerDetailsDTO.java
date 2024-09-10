package com.houseclay.zebra.dto.AddPropertyDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class OwnerDetailsDTO {
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String email;
    private String notes;

}

