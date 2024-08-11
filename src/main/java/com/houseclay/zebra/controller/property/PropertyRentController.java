package com.houseclay.zebra.controller.property;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.houseclay.zebra.dto.PropertyRentDTO;

import com.houseclay.zebra.dto.SaveAndUrlResponseDTO;
import com.houseclay.zebra.dto.ViewAllPropertiesDTO;
import com.houseclay.zebra.model.common.BaseTimeStamp;
import com.houseclay.zebra.model.rental.Images;
import com.houseclay.zebra.model.rental.PropertyRent;
import com.houseclay.zebra.repository.PropertyForRentRepository;
import com.houseclay.zebra.service.ImagesService;
import com.houseclay.zebra.service.impl.PropertyRentServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@RestController
@Api(tags = "Rental Properties")
//@RequestMapping(value = "/post-property-for-rent")
@RequestMapping(value="/properties")
public class PropertyRentController {


    @Autowired
    PropertyRentServiceImpl propertyRentService;
    @Autowired
    ImagesService imagesService;

    @ApiOperation(value="Post a Property",response = PropertyRent.class)
    @Consumes({MediaType.MULTIPART_FORM_DATA_VALUE})
    @PostMapping(value="/v2/residential")
    public ResponseEntity<SaveAndUrlResponseDTO> saveResidentialPropertyForRentv2(@RequestPart("property_for_rent") String jsonProperty,
                                                                                  @RequestPart("files") List<MultipartFile> files)
    {
        PropertyRent property_for_rent = propertyRentService.getJson(jsonProperty,files);
//        PropertyRent data = propertyRentService.saveResidentialPropertyForRentv2(property_for_rent);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(data);
        SaveAndUrlResponseDTO saveAndUrlResponseDTO=propertyRentService.saveResidentialPropertyForRentv2(property_for_rent);
        return ResponseEntity.status(HttpStatus.OK).body(saveAndUrlResponseDTO);

    }


    //Get all the details of the property including the owner Details
    @GetMapping("/v1/detail/{id}")
    public ResponseEntity<PropertyRentDTO> propertyRentDetails(@PathVariable("id") UUID property_id){

        PropertyRentDTO propertyRentDTO = propertyRentService.findPropertyForRentById(property_id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(propertyRentDTO);

    }



    @DeleteMapping("/v1/delete/{id}")
    public ResponseEntity<String> deleteResidentialPropertyForRent(@PathVariable("id") UUID property_id){

        Boolean value= propertyRentService.deletePropertyForRentById(property_id);
        if(value){
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body("Deleted");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not Delete as Property is Not Available");
        }

    }

    @PutMapping
    public ResponseEntity<String> updateResidentialPropertyForRent(@PathVariable("id") UUID property_id){

        return null;
    }


    /**
     * ----------------------------BELOW DEPRECATED ENDPOINTS---------------------
     */


    /**
     * Deprecated EndPoint use v2
     * @param property_for_rent
     * @return
     */
    @Deprecated
    @PostMapping("/v1/residential")
    @Consumes({MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<PropertyRent> saveResidentialPropertyForRentv1(@RequestBody PropertyRent property_for_rent){
        return new ResponseEntity<PropertyRent>(propertyRentService.saveResidentialPropertyForRent(property_for_rent),
                HttpStatus.CREATED);
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


    //           ---------------------------------------





    // view all properties
    @GetMapping("/view-properties")
    public ResponseEntity<List<ViewAllPropertiesDTO>> viewAllDetails(){
        List<ViewAllPropertiesDTO>l=propertyRentService.viewAllDetails();
        return ResponseEntity.status(HttpStatus.OK).body(l);
    }



    // view specific property
    @GetMapping("/view-properties/{property_id}")
    public ResponseEntity<PropertyRent>viewSpecificProperty(@PathVariable("property_id") UUID property_id) throws FileNotFoundException {
            PropertyRent propertyRent=propertyRentService.viewSpecificProperty(property_id);
            return ResponseEntity.status(HttpStatus.OK).body(propertyRent);
    }



    // delete specific property
    @DeleteMapping("/delete-property/{property_id}")
    public ResponseEntity<String> deleteProperty(@PathVariable("property_id") UUID property_id){
        Boolean value= propertyRentService.deletePropertyForRentById(property_id);
        if(value){
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body("Deleted");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not Delete as Property is Not Available");
        }
    }






    // save property using multipart images
    @PostMapping("post-new-rental-property/multipartImage")
    @ApiOperation(value="Post a Property 2 ",response = PropertyRent.class)
    @Consumes({MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> saveProperty(@RequestPart("property-for-rent") String jsonProperty,
                                                                                  @RequestPart("images") List<MultipartFile> images)
    {
        String folderUrl=propertyRentService.saveProperty(jsonProperty, images);
        return ResponseEntity.status(HttpStatus.OK).body(folderUrl);
    }



    // update property using multipart images
    @PutMapping("updateProperty/multipart/{propertyId}")
    @ApiOperation(value="update property")
    @Consumes({MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> updateProperty2(@PathVariable("propertyId") UUID propertyId, @RequestPart("property-for-rent") String jsonProperty, @RequestPart("images") List<MultipartFile>images) throws FileNotFoundException {
        PropertyRent propertyRent=propertyRentService.convertStringToObject(jsonProperty);
        propertyRentService.updatePropertyMultipartData(propertyId, propertyRent, images);
        return ResponseEntity.status(HttpStatus.OK).body("successfully updated !");
    }




}
