package com.houseclay.zebra.service;

import com.houseclay.zebra.dto.LocationDTO;
import com.houseclay.zebra.exceptionHandling.IdNotFoundException;
import com.houseclay.zebra.model.Configure.Location;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationService {

    public Location addLocation(LocationDTO locationDTO, String username);
    public Location editLocation(UUID locationId, LocationDTO locationDTO,  String username) throws IdNotFoundException;
    public boolean deleteLocation(UUID locationId, List<String> roles) throws IdNotFoundException;
    public ResponseEntity<List<Location>> viewAllLocations();

    public boolean checkForExistingLocation(LocationDTO locationDTO);
}
