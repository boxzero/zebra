package com.houseclay.zebra.controller.configure;


import com.houseclay.zebra.controller.BaseController;
import com.houseclay.zebra.dto.LocationDTO;
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
public class LocationController extends BaseController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/add-location")
    public ResponseEntity<Location> addLocation(@RequestBody LocationDTO locationDTO, @RequestHeader("Authorization") String token){
        Location location = locationService.addLocation(locationDTO, findUsernameFromHeader(token));
        if (location != null) {
            return ResponseEntity.ok(location);
        } else {
            return ResponseEntity.badRequest().build();
        }
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
