package com.houseclay.zebra.service;

import com.houseclay.zebra.dto.PropertyRentDTO;
import com.houseclay.zebra.dto.SaveAndUrlResponseDTO;
import com.houseclay.zebra.dto.ViewAllPropertiesDTO;
import com.houseclay.zebra.model.rental.PropertyRent;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PropertRentService {

    PropertyRent saveResidentialPropertyForRent(PropertyRent property_for_rent);

    SaveAndUrlResponseDTO saveResidentialPropertyForRentv2(PropertyRent property_for_rent);

    PropertyRentDTO findPropertyForRentById(UUID uuid);

    Boolean deletePropertyForRentById(UUID uuid);

    PropertyRent updatePropertyForRentById(UUID uuid);


    String saveProperty(String jsonProperty, List<MultipartFile> images);

    PropertyRent updatePropertyMultipartData(UUID propertyId, PropertyRent newProperty, List<MultipartFile> images) throws FileNotFoundException;


    List<ViewAllPropertiesDTO>viewAllDetails();

}
