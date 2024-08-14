package com.houseclay.zebra.controller.configure;


import com.houseclay.zebra.controller.BaseController;
import com.houseclay.zebra.dto.LocationDTO;
import com.houseclay.zebra.exceptionHandling.IdNotFoundException;
import com.houseclay.zebra.model.Configure.Location;
import com.houseclay.zebra.service.LocationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Api(tags = "Location")
@RequestMapping(value="/configure")
@Controller
public class LocationController extends BaseController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/add-location")
    public ResponseEntity<?> addLocation(@RequestBody LocationDTO locationDTO, @RequestHeader("Authorization") String token){
        boolean isduplicate=locationService.checkForExistingLocation(locationDTO);
        if(isduplicate){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Location Already there");
        }

        Location location = locationService.addLocation(locationDTO, findUsernameFromHeader(token));
        if (location != null) {
            return ResponseEntity.ok(location);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/edit-location/{locationId}")
    public ResponseEntity<Location> editLocation(@PathVariable("locationId") UUID locationId, @RequestBody LocationDTO locationDTO, @RequestHeader("Authorization") String token) throws IdNotFoundException {
        Location location = locationService.editLocation(locationId, locationDTO, findUsernameFromHeader(token));
        if (location != null) {
            return ResponseEntity.ok(location);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/view-all-locations")
    public ResponseEntity<List<Location>> viewAllLocations(){
        return locationService.viewAllLocations();
    }

    @DeleteMapping("/delete-location/{locationId}")
    public ResponseEntity<String> deleteLocation(@PathVariable("locationId") UUID locationId, @RequestHeader("Authorization") String token) throws IdNotFoundException {
        boolean authorizedToDeleteLocation=locationService.deleteLocation(locationId, extractRoleFromToken(token));
        if(!authorizedToDeleteLocation){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied !");
        }
        return ResponseEntity.ok("Location Deleted Successfully");
    }

}
