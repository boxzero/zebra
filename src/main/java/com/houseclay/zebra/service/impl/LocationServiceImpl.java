package com.houseclay.zebra.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.houseclay.zebra.model.Configure.Location;
import com.houseclay.zebra.model.common.BaseTimeStamp;
import com.houseclay.zebra.repository.LocationRepository;
import com.houseclay.zebra.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocationServiceImpl implements LocationService {


    @Autowired
    private LocationRepository locationRepository;

    @Override
    public ResponseEntity<Location> addLocation(String jsonProperty) {
        Location location=convertJsonToLocation(jsonProperty);
        BaseTimeStamp baseTimeStamp=BaseTimeStamp.builder().created_by("SYSTEM").created_on(new Date()).changed_on(null).changed_by(null).build();
        location.setBaseTimeStamp(baseTimeStamp);
        return  ResponseEntity.status(HttpStatus.OK).body(locationRepository.save(location));

    }

    @Override
    public ResponseEntity<Location> editLocation(UUID locationId, String jsonProperty) throws FileNotFoundException {
        Optional<Location> location = locationRepository.findById(locationId);
        if(!location.isPresent()){
            throw new FileNotFoundException("File Not Found !");
        }

            Location existingLocation = location.get();


            BaseTimeStamp baseTimeStamp=existingLocation.getBaseTimeStamp();
            baseTimeStamp.setChanged_on(new Date());
            baseTimeStamp.setChanged_by("SYSTEM");

            Location newLocation = convertJsonToLocation(jsonProperty);

            existingLocation.setName(newLocation.getName());
            existingLocation.setPincode(newLocation.getPincode());
            existingLocation.setCity(newLocation.getCity());
            existingLocation.setBaseTimeStamp(baseTimeStamp);

            locationRepository.save(existingLocation);
            return ResponseEntity.status(HttpStatus.OK).body(existingLocation);



    }

    @Override
    public ResponseEntity<String> deleteLocation(UUID locationId) throws FileNotFoundException{
        Optional<Location> location = locationRepository.findById(locationId);
        if(!location.isPresent()){
            throw new FileNotFoundException("file not found !");
        }
        locationRepository.deleteById(locationId);
        return ResponseEntity.status(HttpStatus.OK).body("deleted !");

    }

    @Override
    public ResponseEntity<List<Location>> viewAllLocations() {
        List<Location> allLocations=locationRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(allLocations);
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
