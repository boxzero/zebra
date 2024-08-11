package com.houseclay.zebra.dto;

import com.houseclay.zebra.model.rental.PropertyRent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveAndUrlResponseDTO {
    private List<String> urls;
    private PropertyRent propertyRent;
}
