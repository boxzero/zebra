package com.houseclay.zebra.service.impl;

import com.houseclay.zebra.dto.CallDTO;
import com.houseclay.zebra.dto.CallHistoryDTO;
import com.houseclay.zebra.exceptionHandling.IdNotFoundException;
import com.houseclay.zebra.model.common.BaseTimeStamp;
import com.houseclay.zebra.model.lead.*;
import com.houseclay.zebra.repository.*;
import com.houseclay.zebra.service.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
@Slf4j
public class CallHistoryServiceImpl implements CallHistoryService {


    @Autowired private CallHistoryRepository callHistoryRepository;
    @Autowired private OwnerLeadRepository ownerLeadRepository;
    @Autowired private OwnerLeadService ownerLeadService;
    @Autowired private BuyerLeadRepository buyerLeadRepository;
    @Autowired private BuyerLeadService buyerLeadService;
    @Autowired private TenantLeadRepository tenantLeadRepository;
    @Autowired private TenantLeadService tenantLeadService;
    @Autowired private SellerLeadRepository sellerLeadRepository;
    @Autowired private SellerLeadService sellerLeadService;





    private CallHistory sanitizeCallDTO(CallDTO callDTO,String loggedInUser){
        CallHistory call = CallHistory.builder()
                .durationOfTheCall(callDTO.getDurationOfTheCall())
                .callNotes(callDTO.getCallNotes())
                .leadFinalStatus(callDTO.getLeadFinalStatus())
                .leadInitialStatus(callDTO.getLeadInitialStatus())
                .called_by(loggedInUser)
                .timeStamp(LocalDateTime.now())
                .baseTimeStamp(BaseTimeStamp.builder()
                        .created_by(loggedInUser)
                        .created_on(new Date())
                        .build())
                .build();
        return call;
    }


    public List<CallHistoryDTO> leadOwnerCallHistory(UUID leadOwnerId) throws  IdNotFoundException{
        Optional<LeadOwner> leadOwner = ownerLeadRepository.findById(leadOwnerId);
        if (!leadOwner.isPresent()) {
            throw new IdNotFoundException(leadOwnerId, "lead owner id not found");
        }


        List<CallHistory> callHistoryList= leadOwner.get().getCallHistory();
        List<CallHistoryDTO> responseList=new ArrayList<>();
        for(CallHistory c : callHistoryList){
            CallHistoryDTO callHistoryDTO = CallHistoryDTO.builder()
                    .called_by(c.getCalled_by())
                    .timeStamp(c.getTimeStamp())
                    .durationOfTheCall(c.getDurationOfTheCall())
                    .leadInitialStatus(c.getLeadInitialStatus())
                    .leadFinalStatus(c.getLeadFinalStatus())
                    .callNotes(c.getCallNotes())
                    .build();
            responseList.add(callHistoryDTO);
        }
        return responseList;
    }

    public List<CallHistoryDTO> leadBuyerCallHistory(UUID leadBuyerId) throws  IdNotFoundException{
        Optional<LeadBuyer> leadBuyer = buyerLeadRepository.findById(leadBuyerId);
        if (!leadBuyer.isPresent()) {
            throw new IdNotFoundException(leadBuyerId, "lead buyer id not found");
        }


        List<CallHistory> callHistoryList= leadBuyer.get().getCallHistory();
        List<CallHistoryDTO> responseList=new ArrayList<>();
        for(CallHistory c : callHistoryList){
            CallHistoryDTO callHistoryDTO = CallHistoryDTO.builder()
                    .called_by(c.getCalled_by())
                    .timeStamp(c.getTimeStamp())
                    .durationOfTheCall(c.getDurationOfTheCall())
                    .leadInitialStatus(c.getLeadInitialStatus())
                    .leadFinalStatus(c.getLeadFinalStatus())
                    .callNotes(c.getCallNotes())
                    .build();
            responseList.add(callHistoryDTO);
        }
        return responseList;
    }

    public List<CallHistoryDTO> leadSellerCallHistory(UUID leadSellerId) throws  IdNotFoundException{
        Optional<LeadSeller> leadSeller = sellerLeadRepository.findById(leadSellerId);
        if (!leadSeller.isPresent()) {
            throw new IdNotFoundException(leadSellerId, "lead seller id not found");
        }


        List<CallHistory> callHistoryList= leadSeller.get().getCallHistory();
        List<CallHistoryDTO> responseList=new ArrayList<>();
        for(CallHistory c : callHistoryList){
            CallHistoryDTO callHistoryDTO = CallHistoryDTO.builder()
                    .called_by(c.getCalled_by())
                    .timeStamp(c.getTimeStamp())
                    .durationOfTheCall(c.getDurationOfTheCall())
                    .leadInitialStatus(c.getLeadInitialStatus())
                    .leadFinalStatus(c.getLeadFinalStatus())
                    .callNotes(c.getCallNotes())
                    .build();
            responseList.add(callHistoryDTO);
        }
        return responseList;
    }

    public List<CallHistoryDTO> leadTenantCallHistory(UUID leadTenantId) throws  IdNotFoundException{
        Optional<LeadTenant> leadTenant = tenantLeadRepository.findById(leadTenantId);
        if (!leadTenant.isPresent()) {
            throw new IdNotFoundException(leadTenantId, "lead tenant id not found");
        }


        List<CallHistory> callHistoryList= leadTenant.get().getCallHistory();
        List<CallHistoryDTO> responseList=new ArrayList<>();
        for(CallHistory c : callHistoryList){
            CallHistoryDTO callHistoryDTO = CallHistoryDTO.builder()
                    .called_by(c.getCalled_by())
                    .timeStamp(c.getTimeStamp())
                    .durationOfTheCall(c.getDurationOfTheCall())
                    .leadInitialStatus(c.getLeadInitialStatus())
                    .leadFinalStatus(c.getLeadFinalStatus())
                    .callNotes(c.getCallNotes())
                    .build();
            responseList.add(callHistoryDTO);
        }
        return responseList;
    }
    @Override
    public String addCallToOwner(CallDTO callDTO, String loggedInUser, UUID leadOwnerId) {
        CallHistory call = sanitizeCallDTO(callDTO, loggedInUser);
        try {
                Optional<LeadOwner> leadOwner = ownerLeadRepository.findById(leadOwnerId);
                if (!leadOwner.isPresent()) {
                    throw new IdNotFoundException(leadOwnerId, "lead owner id not found");
                }
            leadOwner.get().addCallToCallHistory(call);
            ownerLeadService.saveAndChangeLeadOwnerStatus(call.getLeadFinalStatus(), leadOwner.get());
            }catch(Exception exception){
                log.error("Exception occured in saving call to callHistory", exception);
                return "call cannot be saved to callHistory";
            }
        return "Call added to call history of owner lead";
    }

    @Override
    public String addCallToBuyer(CallDTO callDTO, String loggedInUser, UUID leadBuyerId) {
        CallHistory call = sanitizeCallDTO(callDTO, loggedInUser);
        try {
            Optional<LeadBuyer> leadBuyer = buyerLeadRepository.findById(leadBuyerId);
            if (!leadBuyer.isPresent()) {
                throw new IdNotFoundException(leadBuyerId, "lead buyer id not found");
            }
            leadBuyer.get().addCallToCallHistory(call);
            buyerLeadService.saveAndChangeLeadBuyerStatus(call.getLeadFinalStatus(), leadBuyer.get());
        }catch(Exception exception){
            log.error("Exception occured in saving call to callHistory", exception);
            return "call cannot be saved to callHistory";
        }
        return "Call added to call history of buyer lead";
    }

    @Override
    public String addCallToSeller(CallDTO callDTO, String loggedInUser, UUID leadSellerId) {
        CallHistory call = sanitizeCallDTO(callDTO, loggedInUser);
        try {
            Optional<LeadSeller> leadSeller = sellerLeadRepository.findById(leadSellerId);
            if (!leadSeller.isPresent()) {
                throw new IdNotFoundException(leadSellerId, "lead seller id not found");
            }
            leadSeller.get().addCallToCallHistory(call);
            sellerLeadService.saveAndChangeLeadSellerStatus(call.getLeadFinalStatus(), leadSeller.get());
        }catch(Exception exception){
            log.error("Exception occured in saving call to callHistory", exception);
            return "call cannot be saved to callHistory";
        }
        return "Call added to call history of seller lead";
    }

    @Override
    public String addCallToTenant(CallDTO callDTO, String loggedInUser, UUID leadTenantId) {
        CallHistory call = sanitizeCallDTO(callDTO, loggedInUser);
        try {
            Optional<LeadTenant> leadTenant = tenantLeadRepository.findById(leadTenantId);
            if (!leadTenant.isPresent()) {
                throw new IdNotFoundException(leadTenantId, "lead tenant id not found");
            }
            leadTenant.get().addCallToCallHistory(call);
            tenantLeadService.saveAndChangeLeadTenantStatus(call.getLeadFinalStatus(), leadTenant.get());
        }catch(Exception exception){
            log.error("Exception occured in saving call to callHistory", exception);
            return "call cannot be saved to callHistory";
        }
        return "Call added to call history of tenant lead";
    }

}
