package com.houseclay.zebra.service;

import com.houseclay.zebra.model.PropertyRent;
import org.springframework.http.ResponseEntity;
import sun.misc.UUDecoder;

import java.util.UUID;

public interface PropertRentService {

    PropertyRent saveResidentialPropertyForRent(PropertyRent property_for_rent);

    PropertyRent findPropertyForRentById(UUID uuid);

    Boolean deletePropertyForRentById(UUID uuid);

    PropertyRent updatePropertyForRentById(UUID uuid);


}
