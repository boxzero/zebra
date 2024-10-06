package com.houseclay.zebra.dto;

import lombok.*;

import java.util.Date;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ViewAllPropertiesDTO {


    private UUID property_id;
    private String name;
    private String bhkType;
    private String locality;
    private double rent;
    private Date availableFrom;
    private String furniture;

}
