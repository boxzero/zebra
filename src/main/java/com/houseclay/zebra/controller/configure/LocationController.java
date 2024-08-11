package com.houseclay.zebra.controller.configure;


import com.houseclay.zebra.model.Configure.Location;
import com.houseclay.zebra.service.LocationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

@Api(tags = "Location")
@RequestMapping(value="/configure")
@Controller
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/add-Location")
    public ResponseEntity<Location> addLocation(@RequestBody String jsonProperty){
        return locationService.addLocation(jsonProperty);
    }

    @PutMapping("/edit-location/{locationId}")
    public ResponseEntity<Location> editLocation(@RequestBody String jsonProperty, @PathVariable("locationId") UUID locationId) throws FileNotFoundException {
        return locationService.editLocation(locationId, jsonProperty);
    }

    @GetMapping("/view-all-locations")
    public ResponseEntity<List<Location>> viewAllLocations(){
        return locationService.viewAllLocations();
    }

    @DeleteMapping("/delete-location/{locationId}")
    public ResponseEntity<String> deleteLocation(@PathVariable("locationId") UUID locationId) throws FileNotFoundException {
        return locationService.deleteLocation(locationId);
    }

}
