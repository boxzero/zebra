package com.houseclay.zebra.controller.leads;


import com.houseclay.zebra.controller.BaseController;
import com.houseclay.zebra.dto.CallDTO;
import com.houseclay.zebra.dto.CallHistoryDTO;
import com.houseclay.zebra.exceptionHandling.IdNotFoundException;
import com.houseclay.zebra.model.lead.CallHistory;
import com.houseclay.zebra.service.CallHistoryService;
import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value ="/call-history")
@Api(tags ="Call History")
public class CallHistoryController extends BaseController {


    @Autowired
    private CallHistoryService callHistoryService;



    @GetMapping("/v1/fetch/owner-lead/{id}")
    public ResponseEntity<List<CallHistoryDTO>> fetchCallHistoryOwner(@PathVariable("id") UUID leadOwnerId) throws  IdNotFoundException{
        List<CallHistoryDTO> callHistory = callHistoryService.leadOwnerCallHistory(leadOwnerId);
        return ResponseEntity.ok(callHistory);
    }

    @GetMapping("/v1/fetch/buyer-lead/{id}")
    public ResponseEntity<List<CallHistoryDTO>> fetchCallHistoryBuyer(@PathVariable("id") UUID leadBuyerId) throws  IdNotFoundException{
        List<CallHistoryDTO> callHistory = callHistoryService.leadBuyerCallHistory(leadBuyerId);
        return ResponseEntity.ok(callHistory);
    }

    @GetMapping("/v1/fetch/seller-lead/{id}")
    public ResponseEntity<List<CallHistoryDTO>> fetchCallHistorySeller(@PathVariable("id") UUID leadSellerId) throws  IdNotFoundException{
        List<CallHistoryDTO> callHistory = callHistoryService.leadSellerCallHistory(leadSellerId);
        return ResponseEntity.ok(callHistory);
    }

    @GetMapping("/v1/fetch/tenant-lead/{id}")
    public ResponseEntity<List<CallHistoryDTO>> fetchCallHistoryTenant(@PathVariable("id") UUID leadTenantId) throws  IdNotFoundException{
        List<CallHistoryDTO> callHistory = callHistoryService.leadTenantCallHistory(leadTenantId);
        return ResponseEntity.ok(callHistory);
    }



    @PostMapping("/v1/add/owner-lead/{owner_id}")
    public ResponseEntity<String> addCallToOwner(@PathVariable("owner_id") UUID owner_id, @RequestBody CallDTO callDTO, @RequestHeader("Authorization") String token){
        return ResponseEntity.status(HttpStatus.OK).body(callHistoryService.addCallToOwner(callDTO, findUsernameFromHeader(token), owner_id));
    }

    @PostMapping("/v1/add/buyer-lead/{buyer_id}")
    public ResponseEntity<String> addCallToBuyer(@PathVariable("buyer_id") UUID buyer_id, @RequestBody CallDTO callDTO, @RequestHeader("Authorization") String token){
        return ResponseEntity.status(HttpStatus.OK).body(callHistoryService.addCallToBuyer(callDTO, findUsernameFromHeader(token), buyer_id));
    }

    @PostMapping("/v1/add/seller-lead/{seller_id}")
    public ResponseEntity<String> addCallToSeller(@PathVariable("seller_id") UUID seller_id, @RequestBody CallDTO callDTO, @RequestHeader("Authorization") String token){
        return ResponseEntity.status(HttpStatus.OK).body(callHistoryService.addCallToSeller(callDTO, findUsernameFromHeader(token), seller_id));
    }

    @PostMapping("/v1/add/tenant-lead/{tenant_id}")
    public ResponseEntity<String> addCallToTenant(@PathVariable("tenant_id") UUID tenant_id, @RequestBody CallDTO callDTO, @RequestHeader("Authorization") String token){
        return ResponseEntity.status(HttpStatus.OK).body(callHistoryService.addCallToTenant(callDTO, findUsernameFromHeader(token), tenant_id));
    }

}
