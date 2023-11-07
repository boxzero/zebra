package com.houseclay.zebra.service.impl;

import com.auth0.jwt.JWT;
import com.houseclay.zebra.dto.ImageResponse;
import com.houseclay.zebra.dto.NewLeadTenantDTO;
import com.houseclay.zebra.dto.PropertyRentDTO;
import com.houseclay.zebra.model.User;
import com.houseclay.zebra.model.common.BaseTimeStamp;
import com.houseclay.zebra.model.lead.Lead;
import com.houseclay.zebra.model.lead.LeadTenant;
import com.houseclay.zebra.model.lead.enums.LeadSource;
import com.houseclay.zebra.model.lead.enums.LeadStatus;
import com.houseclay.zebra.model.lead.enums.PropertyType;
import com.houseclay.zebra.repository.TenantLeadRepository;
import com.houseclay.zebra.service.TenantLeadService;
import com.houseclay.zebra.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static com.houseclay.zebra.model.lead.enums.LeadStatus.NEW;
import static com.houseclay.zebra.model.lead.enums.PropertyType.GATED_APARTMENT;
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
    /**
     * This method is for converting the json String to PropertyRent object using ObjectMapper
     * It is also binding the data of the Image List of Files into the PropertyRent Object
     * @param uuid
     * @return
     */
    public NewLeadTenantDTO findTenantLeadById(UUID uuid) {
        Optional<LeadTenant> newLeadTenantByID= tenantLeadRepository.findById(uuid);
        NewLeadTenantDTO newLeadTenantDTO;
        try{
            LeadTenant newLeadTenant = newLeadTenantByID.get();
            newLeadTenantDTO= mapToNewLeadTenantDTO(newLeadTenant);
        }
        catch (Exception e){
            throw new NullPointerException("The Property is Not Present");
        }
        return newLeadTenantDTO;
    }

    @Override
    public NewLeadTenantDTO updateTenantLeadById(NewLeadTenantDTO newLeadTenantDTO, UUID uuid) {
        Optional<LeadTenant> newLeadTenantByID= tenantLeadRepository.findById(uuid);
        NewLeadTenantDTO newLeadTenantdto;

        try{
            LeadTenant  newLeadTenant = newLeadTenantByID.get();
            newLeadTenant.setLead(newLeadTenantDTO.getLead());
            newLeadTenant.setMinBudget(newLeadTenantDTO.getMinBudget());
            newLeadTenant.setMaxBudget(newLeadTenantDTO.getMaxBudget());
            newLeadTenant.setPropertyType(newLeadTenantDTO.getPropertyType());
            newLeadTenant.setPreferredLocations(objectMapperUtils.extractList(newLeadTenantDTO.getPreferredLocations()));
            newLeadTenant.setAssetConfigurations(objectMapperUtils.extractList(newLeadTenantDTO.getAssetConfigurations()));
            newLeadTenant.setOccupancyDate(newLeadTenantDTO.getOccupancyDate());
            newLeadTenant.setIsDateFlexible(newLeadTenantDTO.getIsDateFlexible());
            newLeadTenant.setTenantType(newLeadTenantDTO.getTenantType());
            newLeadTenant.setIsVegetarian(newLeadTenantDTO.getIsVegetarian());
            newLeadTenant.setIsHavingPets(newLeadTenantDTO.getIsHavingPets());
            newLeadTenant.setIsLookingForARoom(newLeadTenantDTO.getIsLookingForARoom());

            LeadTenant newLeadTen = this.tenantLeadRepository.save(newLeadTenant);
            newLeadTenantdto= mapToNewLeadTenantDTO(newLeadTen);
        }
        catch (Exception e){
            throw new NullPointerException("The Property is Not Present");
        }
        return newLeadTenantdto;
    }

    private NewLeadTenantDTO mapToNewLeadTenantDTO(LeadTenant leadTenant)
 {
     return NewLeadTenantDTO.builder()
             .lead(Lead.builder()
                     .firstName(leadTenant.getLead().getFirstName())
                     .lastName(leadTenant.getLead().getLastName())
                     .emailId(leadTenant.getLead().getEmailId())
                     .contactNumber(leadTenant.getLead().getContactNumber())
                     .isEmailVerified(leadTenant.getLead().getIsEmailVerified())
                     .isPhoneVerified(leadTenant.getLead().getIsPhoneVerified())
                     .notes(leadTenant.getLead().getNotes())
                     .leadType(leadTenant.getLead().getLeadType())
                     .leadSource(leadTenant.getLead().getLeadSource())
                     .baseTimeStamp(BaseTimeStamp.builder()
                             .created_by(leadTenant.getLead().getBaseTimeStamp().getCreated_by())
                             .created_on(leadTenant.getLead().getBaseTimeStamp().getCreated_on())
                             .changed_by(leadTenant.getLead().getBaseTimeStamp().getChanged_by())
                             .changed_on(leadTenant.getLead().getBaseTimeStamp().getChanged_on())
                             .build())
                     .build())
             .minBudget(leadTenant.getMinBudget())
             .maxBudget(leadTenant.getMaxBudget())
             .propertyType(leadTenant.getPropertyType())
             //.leadStatus(String.valueOf(LeadStatus.valueOf(NEW.label)))
             .preferredLocations(new ArrayList<>(Arrays.asList(leadTenant.getPreferredLocations().split("-"))))
             .assetConfigurations(new ArrayList<>(Arrays.asList(leadTenant.getAssetConfigurations().split("-"))))
             .occupancyDate(leadTenant.getOccupancyDate())
             .isDateFlexible(leadTenant.getIsDateFlexible())
             .tenantType(leadTenant.getTenantType())
             .isVegetarian(leadTenant.getIsVegetarian())
             .isHavingPets(leadTenant.getIsHavingPets())
             .isLookingForARoom(leadTenant.getIsLookingForARoom())
             .build();
 }

    private LeadTenant newLeadDTOMapper(NewLeadTenantDTO newLeadTenantDTO) {
        return LeadTenant.builder().
                lead(Lead.builder().firstName(newLeadTenantDTO.getLead().getFirstName())
                                .lastName(newLeadTenantDTO.getLead().getLastName())
                                .emailId(newLeadTenantDTO.getLead().getEmailId())
                                .contactNumber(newLeadTenantDTO.getLead().getContactNumber())
                                .isEmailVerified(newLeadTenantDTO.getLead().getIsEmailVerified())
                                .isPhoneVerified(newLeadTenantDTO.getLead().getIsPhoneVerified())
                                .notes(newLeadTenantDTO.getLead().getNotes())
                                .leadType(newLeadTenantDTO.getLead().getLeadType())
                                .leadSource(newLeadTenantDTO.getLead().getLeadSource())
                                .baseTimeStamp(BaseTimeStamp.builder().created_by(newLeadTenantDTO.getLead().getBaseTimeStamp().getCreated_by()).created_on(Date.from(Instant.now()))
                                        .changed_by("gajanan").changed_on(Date.from(Instant.now())) .build())
                        .build()).minBudget(newLeadTenantDTO.getMinBudget()).maxBudget(newLeadTenantDTO.getMaxBudget())
                .propertyType(newLeadTenantDTO.getPropertyType()).leadStatus(String.valueOf(LeadStatus.valueOf(NEW.label)))
                .preferredLocations(objectMapperUtils.extractList(newLeadTenantDTO.getPreferredLocations()))
                .assetConfigurations(objectMapperUtils.extractList(newLeadTenantDTO.getAssetConfigurations()))
                .occupancyDate(newLeadTenantDTO.getOccupancyDate()).isDateFlexible(newLeadTenantDTO.getIsDateFlexible()).tenantType(newLeadTenantDTO.getTenantType())
                .isVegetarian(newLeadTenantDTO.getIsVegetarian()).isHavingPets(newLeadTenantDTO.getIsHavingPets()).isLookingForARoom(newLeadTenantDTO.getIsLookingForARoom())
                .build();
    }
}
