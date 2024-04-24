package com.houseclay.zebra.controller.leads;

import com.houseclay.zebra.controller.BaseController;
import com.houseclay.zebra.dto.OwnerLeadDTO;
import com.houseclay.zebra.service.impl.OwnerLeadServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value ="/owner-leads")
@Api(tags ="Owner Leads")
public class OwnerLeadController extends BaseController {


    @Autowired OwnerLeadServiceImpl ownerLeadService;
    /**
     * Adding an Owner Lead
     * @return
     */
    @PostMapping("/v1/register-owner-lead")
    public ResponseEntity<String> addOwnerLead(@RequestBody OwnerLeadDTO ownerLeadDTO, @RequestHeader("Authorization") String token){

        System.out.println(ownerLeadDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ownerLeadService.addOwnerLead(ownerLeadDTO,findUsernameFromHeader(token)));
    }

    /**
     * Fetching All Owner Leads
     * @return
     */
    public ResponseEntity<String> fetchAll(){

        return null;
    }


}
