package com.houseclay.zebra.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.houseclay.zebra.model.Images;
import com.houseclay.zebra.model.PropertyRent;
import com.houseclay.zebra.repository.PropertyForRentRepository;
import com.houseclay.zebra.service.PropertRentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class PropertyRentServiceImpl implements PropertRentService {

    @Autowired
    PropertyForRentRepository propertyForRentRepository;


    @Override
    public PropertyRent saveResidentialPropertyForRent(PropertyRent property_for_rent) {
        return propertyForRentRepository.save(property_for_rent);
    }

    public PropertyRent saveResidentialPropertyForRentv2(PropertyRent property_for_rent)  {
        return propertyForRentRepository.save(property_for_rent);
    }

    @Override
    public PropertyRent findPropertyForRentById(UUID uuid) {
        return null;
    }

    @Override
    public Boolean deletePropertyForRentById(UUID uuid) {
        return null;
    }

    @Override
    public PropertyRent updatePropertyForRentById(UUID uuid) {
        return null;
    }

    /**
     * This method is for converting the json String to PropertyRent object using ObjectMapper
     * It is also binding the data of the Image List of Files into the PropertyRent Object
     * @param jsonProperty
     * @param files
     * @return
     */
    public PropertyRent getJson(String jsonProperty, List<MultipartFile> files){

        log.info("Inside getJson Method---- PropertyRentServiceImpl");
        PropertyRent propertyRentJson = new PropertyRent();

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            propertyRentJson = objectMapper.readValue(jsonProperty,PropertyRent.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        List<Images> list_of_Images = new ArrayList<Images>();
        for(MultipartFile f: files){
            Images image = null;
            try {
                image = Images.builder().
                        name(StringUtils.cleanPath(f.getOriginalFilename())).
                        contentType(f.getContentType())
                        .size(f.getSize()).image_data(f.getBytes()).build();
            } catch (IOException e) {
                e.printStackTrace();
            }
            list_of_Images.add(image);
        }

        propertyRentJson.setImageMap(list_of_Images);
        return propertyRentJson;
    }
}
