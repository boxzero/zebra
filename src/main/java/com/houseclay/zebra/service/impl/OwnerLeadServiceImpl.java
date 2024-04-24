package com.houseclay.zebra.service.impl;

import com.houseclay.zebra.dto.OwnerLeadDTO;
import com.houseclay.zebra.model.common.BaseTimeStamp;
import com.houseclay.zebra.model.lead.Lead;
import com.houseclay.zebra.model.lead.LeadOwner;
import com.houseclay.zebra.model.lead.enums.*;
import com.houseclay.zebra.repository.OwnerLeadRepository;
import com.houseclay.zebra.service.OwnerLeadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class OwnerLeadServiceImpl implements OwnerLeadService {

    @Autowired OwnerLeadRepository ownerLeadRepository;
    @Override
    public String addOwnerLead(OwnerLeadDTO ownerLeadDTO, String username) {
        //sanitize the dto and map with object , then save the object
        try {
            //check if same lead exists or not - combination of phone number
            Optional<LeadOwner> leadOwner = ownerLeadRepository.findByContactNumber(ownerLeadDTO.getContactNumber());
            if(leadOwner.isPresent()) {
                return "Lead already exist with same phone number";
            }
            else {
                ownerLeadRepository.save(sanitizeDTO(ownerLeadDTO, username));
                return "Owner Lead Saved Successfully";
            }
        }catch(Exception exception){
            log.error("Exception occured in saving Owner Lead", exception);
            return "Owner Lead cannot be saved";
        }

    }

    private LeadOwner sanitizeDTO(OwnerLeadDTO ownerLeadDTO,String username) {
        LeadOwner leadOwner = LeadOwner.builder()
                .lead(Lead.builder().
                        firstName(ownerLeadDTO.getFirstName()).lastName(ownerLeadDTO.getLastName()).contactNumber(ownerLeadDTO.getContactNumber())
                        .emailId(ownerLeadDTO.getEmailId()).leadSource(LeadSource.valueOf(ownerLeadDTO.getLeadSource())).
                        isLeadConverted(false).leadType(LeadType.valueOf(ownerLeadDTO.getLeadType())).isLeadTrashed(false)
                                .isPhoneVerified(ownerLeadDTO.getIsPhoneVerified()).isEmailVerified(ownerLeadDTO.getIsEmailVerified())
                                .trashedReason("").baseTimeStamp(BaseTimeStamp.builder().created_by(username).created_on(new Date()).build()).
                        build()).propertyType(PropertyType.valueOf(ownerLeadDTO.getPropertyType())).bhkType(ownerLeadDTO.getBhkType()).apartmentName(ownerLeadDTO.getApartmentName())
                        .locality(ownerLeadDTO.getLocality()).googleMapLocationURL(ownerLeadDTO.getGoogleMapLocationUrl()).leadStatus(LeadStatus.valueOf("NEW"))
                        .preferredTenants(PreferredTenant.valueOf(ownerLeadDTO.getPreferredTenants())).isNonVegAllowed(ownerLeadDTO.getIsNonVegAllowed())
                        .availableFrom(ownerLeadDTO.getAvailableFrom()).expectedRent(Long.valueOf(ownerLeadDTO.getExpectedRent()))
                        .expectedDeposit(Long.valueOf(ownerLeadDTO.getExpectedDeposit())).furnishing(ownerLeadDTO.getFurnishing()).
                        build();
        log.info("Owner Lead",leadOwner);
        return leadOwner;
    }
}
