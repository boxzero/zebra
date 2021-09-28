package com.houseclay.zebra.controller.tenant;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tenant")
public class TenantController {



    @PostMapping(value = "")
    public ResponseEntity<String> saveTenant(){
        return null;
    }


}
