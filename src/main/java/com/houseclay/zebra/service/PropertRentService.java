package com.houseclay.zebra.service;

import com.houseclay.zebra.dto.PropertyRentDTO;
import com.houseclay.zebra.dto.SaveAndUrlResponseDTO;
import com.houseclay.zebra.dto.ViewAllPropertiesDTO;
import com.houseclay.zebra.dto.ViewSpecificPropertyDTO;
import com.houseclay.zebra.exceptionHandling.IdNotFoundException;
import com.houseclay.zebra.model.rental.PropertyRent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface PropertRentService {

    PropertyRent saveResidentialPropertyForRent(PropertyRent property_for_rent);

    SaveAndUrlResponseDTO saveResidentialPropertyForRentv2(PropertyRent property_for_rent);

    PropertyRentDTO findPropertyForRentById(UUID uuid);

    Boolean deletePropertyForRentById(UUID uuid);

    PropertyRent updatePropertyForRentById(UUID uuid);


    String saveProperty(String jsonProperty, List<MultipartFile> images, String username) ;

    PropertyRent updatePropertyMultipartData(UUID propertyId, PropertyRent newProperty, List<MultipartFile> images, String username) throws IdNotFoundException;


    List<ViewAllPropertiesDTO>viewAllDetails();
    ViewSpecificPropertyDTO viewSpecificProperty(UUID property_id) throws IdNotFoundException;

}
