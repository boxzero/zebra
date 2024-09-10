package com.houseclay.zebra.dto.AddPropertyDTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class PropertyDetailsDTO {

        private String name;
        private String type;
        private String bhkType;
        private int floor;
        private int totalFloor;
        private String propertyAge;
        private String facing;
        private double builtupArea;



}
