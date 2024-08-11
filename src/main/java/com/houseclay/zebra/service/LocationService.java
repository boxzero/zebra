package com.houseclay.zebra.service;

import com.houseclay.zebra.model.Configure.Location;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

public interface LocationService {

    public ResponseEntity<Location>addLocation(String jsonProperty);
    public ResponseEntity<Location>editLocation(UUID locationId, String jsonProperty) throws FileNotFoundException;
    public ResponseEntity<String> deleteLocation(UUID locationId) throws FileNotFoundException;
    public ResponseEntity<List<Location>> viewAllLocations();

}
