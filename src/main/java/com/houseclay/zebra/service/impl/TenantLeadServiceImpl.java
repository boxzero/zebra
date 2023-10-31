package com.houseclay.zebra.service.impl;

import com.houseclay.zebra.dto.NewLeadTenantDTO;
import com.houseclay.zebra.model.common.BaseTimeStamp;
import com.houseclay.zebra.model.lead.Lead;
import com.houseclay.zebra.model.lead.LeadTenant;
import com.houseclay.zebra.model.lead.enums.LeadStatus;
import com.houseclay.zebra.repository.TenantLeadRepository;
import com.houseclay.zebra.service.TenantLeadService;
import com.houseclay.zebra.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.time.Instant;
import java.util.Date;

import static com.houseclay.zebra.model.lead.enums.LeadStatus.NEW;

@Service
public class TenantLeadServiceImpl implements TenantLeadService {

    @Autowired
    TenantLeadRepository tenantLeadRepository;

    @Autowired
    ObjectMapperUtils objectMapperUtils;
    @Override
    public Page<LeadTenant> findAllLeads(Pageable pageable) {
        return tenantLeadRepository.findAll(pageable);
    }

    public LeadTenant addTenantLead(NewLeadTenantDTO newLeadTenantDTO) {
        System.out.println(this.newLeadDTOMapper(newLeadTenantDTO));
        return tenantLeadRepository.save(this.newLeadDTOMapper(newLeadTenantDTO));
    }


    private LeadTenant newLeadDTOMapper(NewLeadTenantDTO newLeadTenantDTO) {
        return LeadTenant.builder().
                lead(Lead.builder().firstName(newLeadTenantDTO.getLead().getFirstName())
                                .lastName(newLeadTenantDTO.getLead().getLastName())
                                .emailId(newLeadTenantDTO.getLead().getEmailId())
                                .contactNumber(newLeadTenantDTO.getLead().getContactNumber())
                                .isEmailVerified(newLeadTenantDTO.getLead().getIsEmailVerified()).isPhoneVerified(newLeadTenantDTO.getLead().getIsPhoneVerified())
                                .notes(newLeadTenantDTO.getLead().getNotes()).leadType(newLeadTenantDTO.getLead().getLeadType())
                                .leadSource(newLeadTenantDTO.getLead().getLeadSource())
                                .baseTimeStamp(BaseTimeStamp.builder().created_by(newLeadTenantDTO.getLead().getBaseTimeStamp().getCreated_by()).created_on(Date.from(Instant.now())).build())
                        .build()).minBudget(newLeadTenantDTO.getMinBudget()).maxBudget(newLeadTenantDTO.getMaxBudget())
                .propertyType(newLeadTenantDTO.getPropertyType()).leadStatus(String.valueOf(LeadStatus.valueOf(NEW.label))).
                preferredLocations(objectMapperUtils.extractPreferredLocationfromList(newLeadTenantDTO.getPreferredLocations())).
                occupancyDate(newLeadTenantDTO.getOccupancyDate()).isDateFlexible(newLeadTenantDTO.getIsDateFlexible()).tenantType(newLeadTenantDTO.getTenantType())
                .isVegetarian(newLeadTenantDTO.getIsVegetarian()).isHavingPets(newLeadTenantDTO.getIsHavingPets()).isLookingForARoom(newLeadTenantDTO.getIsLookingForARoom())
                .build();
    }
}
