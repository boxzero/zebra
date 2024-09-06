package com.houseclay.zebra.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.houseclay.zebra.dto.LocationDTO;
import com.houseclay.zebra.exceptionHandling.IdNotFoundException;
import com.houseclay.zebra.model.Configure.Location;
import com.houseclay.zebra.model.common.BaseTimeStamp;
import com.houseclay.zebra.repository.LocationRepository;
import com.houseclay.zebra.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class LocationServiceImpl implements LocationService {


    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Location addLocation(LocationDTO locationDTO, String loggedInUser) {
        try {
            Location location = Location.builder()
                    .city(locationDTO.getCity())
                    .locationName(locationDTO.getLocationName())
                    .pinCode(locationDTO.getPincode())
                    .state(locationDTO.getState())
                    .baseTimeStamp(BaseTimeStamp.builder()
                            .created_by(loggedInUser)
                            .created_on(new Date())
                            .build())
                    .build();

            location = locationRepository.save(location);
            log.info("Location saved successfully");
            return location;
        } catch (Exception ex) {
            log.error("Unable to save location", ex);
            throw new RuntimeException("Unable to save location", ex); // rethrow the exception
        }
    }

    @Override
    public Location editLocation(UUID locationId, LocationDTO locationDTO, String loggedInUser) throws IdNotFoundException {
        Optional<Location> location = locationRepository.findById(locationId);
        if(!location.isPresent()){
            throw new IdNotFoundException(locationId, "Location Id Not Found !");
        }

        try{
            Location existingLocation = location.get();

            BaseTimeStamp baseTimeStamp = existingLocation.getBaseTimeStamp();
            baseTimeStamp.setChanged_on(new Date());
            baseTimeStamp.setChanged_by(loggedInUser);


            existingLocation.setLocationName(locationDTO.getLocationName());
            existingLocation.setPinCode(locationDTO.getPincode());
            existingLocation.setCity(locationDTO.getCity());
            existingLocation.setState(locationDTO.getState());
            existingLocation.setBaseTimeStamp(baseTimeStamp);

            existingLocation=locationRepository.save(existingLocation);
            log.info("Location updated successfully !");
            return existingLocation;
        }catch(Exception ex){
            log.error("Unable to edit location", ex);
            throw new RuntimeException("Unable to edit location" ,ex);
        }



    }

    @Override
    public String deleteLocation(UUID locationId) throws IdNotFoundException{
        Optional<Location> location = locationRepository.findById(locationId);
        if(!location.isPresent()){
            throw new IdNotFoundException(locationId, "location Id  not found !");
        }

        try {
                locationRepository.deleteById(locationId);
                log.info("Location deleted Successfully !");
        }catch(Exception ex){
            log.error("Unable to Delete location", ex);
            throw new RuntimeException("Unable to Delete location", ex);
        }

       return "Location deleted Successfully !";

    }

    @Override
    public List<Location> viewAllLocations() {
        List<Location> allLocations=locationRepository.findAll();
        return allLocations;
    }

    @Override
    public boolean checkForExistingLocation(LocationDTO locationDTO) {

        Optional<Location> location=locationRepository.findByCityAndLocationNameAndPinCode(locationDTO.getCity(), locationDTO.getLocationName(), locationDTO.getPincode());
        if(location.isPresent()){
            return true;
        }
        return false;
    }

    private Location convertJsonToLocation(String jsonProperty) {
        Location locationJson=new Location();
        try {
            ObjectMapper objectMapper=new ObjectMapper();
            locationJson=objectMapper.readValue(jsonProperty, Location.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return locationJson;
    }
}
