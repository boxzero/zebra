package com.houseclay.zebra.controller.leads;

import com.houseclay.zebra.dto.NewLeadTenantDTO;
import com.houseclay.zebra.model.lead.LeadTenant;
import com.houseclay.zebra.service.impl.TenantLeadServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@Api(tags = "Tenant Leads")
@RequestMapping(value = "/tenant-leads")
public class TenantLeadController {

    @Autowired
    TenantLeadServiceImpl tenantLeadService;
    //add a new tenant lead
    @PostMapping(value = "/add-lead")
    public ResponseEntity<LeadTenant> addTenantLead(@RequestBody NewLeadTenantDTO newLeadTenantDTO){
        //User user = (User) authentication.getPrincipal();
        //leadTenant.getLead().getBaseTimeStamp().setCreated_by(user.getUsername());
        return ResponseEntity.status(HttpStatus.OK)
                .body(tenantLeadService.addTenantLead(newLeadTenantDTO));

    }

    @GetMapping(value = "/get-lead/{id}")
    //get a specific details of a lead
    public ResponseEntity<LeadTenant> getTenantLead(@PathVariable("id")UUID lead_id) {return null;}

    //Get all leads with Pagegable Request
    @RequestMapping(value="/get-leads")
    public Page<LeadTenant> getPagableLeads(@NotNull final Pageable pageable){
        return tenantLeadService.findAllLeads(pageable);
    }
}
