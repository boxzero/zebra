package com.houseclay.zebra.controller;


import com.houseclay.zebra.model.PropertyRent;
import com.houseclay.zebra.service.ImagesService;
import com.houseclay.zebra.service.impl.PropertyRentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.Consumes;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/post-property-for-rent")
public class PropertyRentController {


    @Autowired
    PropertyRentServiceImpl propertyRentService;

    @Autowired
    ImagesService imagesService;
    /**
     * Deprecated EndPoint use v2
     * @param property_for_rent
     * @return
     */
    @Deprecated
    @PostMapping("/v1/residential")
    public ResponseEntity<PropertyRent> saveResidentialPropertyForRentv1(@RequestBody PropertyRent property_for_rent){
        return new ResponseEntity<PropertyRent>(propertyRentService.saveResidentialPropertyForRent(property_for_rent),
                HttpStatus.CREATED);
    }


    /**
     * Endpoint to save a Property with Images
     * @param jsonProperty
     * @param files
     * @return
     */
    @PostMapping("/v2/residential")
    @Consumes({MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<PropertyRent> saveResidentialPropertyForRentv2(@RequestPart("property_for_rent") String jsonProperty,
                                                                         @RequestPart("files") List<MultipartFile> files)
    {
        PropertyRent property_for_rent = propertyRentService.getJson(jsonProperty,files);
        PropertyRent data = propertyRentService.saveResidentialPropertyForRentv2(property_for_rent);
        return ResponseEntity.status(HttpStatus.OK)
                .body(data);

    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(@RequestParam("file")MultipartFile file) {

        try {
            imagesService.save(file);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("File uploaded successfully: %s", file.getOriginalFilename()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not upload the file: %s!", file.getOriginalFilename()));
        }
    }


}
