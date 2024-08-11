package com.houseclay.zebra.dto;

import lombok.*;

import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ViewAllPropertiesDTO {

    private UUID property_id;
    private String name;
    private String title;

}
