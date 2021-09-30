package com.houseclay.zebra.controller.tenant;



import com.houseclay.zebra.model.tenant.Tenant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/tenant-management")
public class TenantController {


    /**
     *
     * addTenant(List)
     * mapTenantToProperty
     * ------------------
     * getAllTenants
     * getTenantById
     * getTenantByProperty
     * -----------
     * activateTenant
     * deactivateTenant
     * removeTenantFromProperty
     * updateTenantProfile
     * -----------------
     *
     * @return
     */

    //Add New Tenant with Deactivated State
    @PostMapping(value = "/v1/save")
    public ResponseEntity<String> saveTenant(@RequestBody Tenant tenant){
        return null;
    }

    //Map Tenant to Property with both uuid
    @PatchMapping(value = "/v1/onboarding/{pid}/{tid}")
    public ResponseEntity<String> mapTenantToProperty(@PathVariable("pid") UUID property_id, @PathVariable("tid") UUID tenant_id){
        return null;
    }

    // Get all Tenants both Active and Deactive
    @GetMapping(value = "/v1/fetchall")
    public ResponseEntity<List<Tenant>> getAllTenants(){
        return  null;
    }

    //Get a specific Tenant By ID
    @GetMapping(value = "/v1/{id}")
    public ResponseEntity<Tenant> getTenantById(){
        return null;
    }

    //Get a specific Tenant By PropertyID
    @GetMapping(value = "/v2/{id}")
    public ResponseEntity<Tenant> getTenantByPropertyId(){
        return null;
    }

    //Activate a Tenant By Id
    @PutMapping(value = "/v1/activate/{id}")
    public ResponseEntity<String> activateTenant(){
        return null;
    }

    //Deactivate a Tenant By ID
    @PatchMapping(value = "/v1/deactivate/{id}")
    public ResponseEntity<String> deactivateTenant(){
        return null;
    }

    @PutMapping(value = "/v1/update-profile")
    public ResponseEntity<String> updateTenantProfile(){
        return null;
    }



}
