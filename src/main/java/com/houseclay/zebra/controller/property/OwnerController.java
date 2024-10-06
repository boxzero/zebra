package com.houseclay.zebra.controller.property;


import com.houseclay.zebra.exceptionHandling.IdNotFoundException;
import com.houseclay.zebra.model.rental.Owner;
import com.houseclay.zebra.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/owner")
public class OwnerController {


    @Autowired
    private OwnerService ownerService;

    @GetMapping("fetchOwner/{owner_id}")
    public ResponseEntity<Owner> fetchOwner(@PathVariable("owner_id") UUID ownerId) throws IdNotFoundException {
        Owner owner = ownerService.fetchOwner(ownerId);
        return ResponseEntity.status(HttpStatus.OK).body(owner);
    }
}
