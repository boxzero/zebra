package com.houseclay.zebra.controller.leads;

import com.houseclay.zebra.model.lead.Lead;
import com.houseclay.zebra.service.LeadService;
import com.houseclay.zebra.service.impl.LeadServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;


@RestController
@Api(tags = "Lead Manager")
@CrossOrigin(origins = {"http://localhost:3000","http://192.168.1.4"},allowCredentials = "true")
@RequestMapping(value = "/lead-manager")
public class LeadController {

    @Autowired
    LeadServiceImpl leadService;


    @PostMapping(value = "/v1/add-lead")
    public ResponseEntity<String> addLead(@RequestBody Lead lead){
        Lead l= leadService.saveNewLead(lead);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Lead Saved Successfully -- "+l.getFirstName());
    }

    //get all leads with trashed
    @GetMapping(value = "/v1/get-all-leads")
    public ResponseEntity<List<Lead>> getAllLeads(){
        return null;
    }

    //get all leads which are not converted
    @GetMapping(value = "/v2/get-all-leads")
    public ResponseEntity<List<Lead>> getAllUnconvertedLeads(){
        return null;
    }

    @GetMapping(value = "/v1/get-lead/{id}")
    public ResponseEntity<Lead> getLeadByID(@PathVariable("id") UUID lead_id){
        return null;
    }

    // Move to Trash - Make isLeadTrashed to True in cases if lead already found property
    @PatchMapping(value = "/v1/move-lead-to-trash/{id}")
    public ResponseEntity<String> moveLeadToTrash(){
        return null;
    }

    @PatchMapping(value = "/v1/pull-lead-from-trash/{id}")
    public ResponseEntity<String> pullLeadFromTrash(){
        return null;
    }

    @PutMapping(value = "/v1/lead-to-tenant")
    public ResponseEntity<String> convertLeadtoTenant(){
        return null;
    }

    @PutMapping(value = "/v1/tenant-to-lead")
    public ResponseEntity<String> convertTenantToLead(){
        return null;
    }

    @PutMapping(value = "/v1/update-lead")
    public ResponseEntity<Lead> updateLeadDetails(){
        return null;
    }

}
