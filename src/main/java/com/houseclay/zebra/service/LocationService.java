package com.houseclay.zebra.service;

import com.houseclay.zebra.dto.LocationDTO;
import com.houseclay.zebra.model.Configure.Location;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

public interface LocationService {

    public Location addLocation(LocationDTO locationDTO, String username);
    public ResponseEntity<Location>editLocation(UUID locationId, String jsonProperty) throws FileNotFoundException;
    public ResponseEntity<String> deleteLocation(UUID locationId) throws FileNotFoundException;
    public ResponseEntity<List<Location>> viewAllLocations();

}
