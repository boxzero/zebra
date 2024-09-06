package com.houseclay.zebra.service;



import com.houseclay.zebra.dto.CallDTO;
import com.houseclay.zebra.dto.CallHistoryDTO;
import com.houseclay.zebra.exceptionHandling.IdNotFoundException;
import com.houseclay.zebra.model.lead.CallHistory;

import java.util.List;
import java.util.UUID;

public interface CallHistoryService {
//    List<CallHistory> fetchCallHistoryByLeadId(UUID uuid);

    List<CallHistoryDTO> leadOwnerCallHistory(UUID leadOwnerId) throws  IdNotFoundException;
    List<CallHistoryDTO> leadSellerCallHistory(UUID leadSellerId) throws  IdNotFoundException;
    List<CallHistoryDTO> leadBuyerCallHistory(UUID leadBuyerId) throws  IdNotFoundException;
    List<CallHistoryDTO> leadTenantCallHistory(UUID leadTenantId) throws  IdNotFoundException;

    String addCallToOwner(CallDTO callDTO, String usernameFromHeader, UUID ownerId);

    String addCallToBuyer(CallDTO callDTO, String usernameFromHeader, UUID buyerId);

    String addCallToSeller(CallDTO callDTO, String usernameFromHeader, UUID sellerId);

    String addCallToTenant(CallDTO callDTO, String usernameFromHeader, UUID tenantId);

//    String addCallHistoryToLead(CallDTO callDTO, String usernameFromHeader, UUID baseLeadId);
//    String addCallDetails(CallDTO callDTO, String username) throws IdNotFoundException;

}
