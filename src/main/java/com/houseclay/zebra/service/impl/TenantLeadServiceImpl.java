package com.houseclay.zebra.service.impl;

import com.auth0.jwt.JWT;
import com.houseclay.zebra.dto.PropertyRentDTO;
import com.houseclay.zebra.dto.TenantLeadDTO;
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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TenantLeadServiceImpl implements TenantLeadService {

    @Autowired
    TenantLeadRepository tenantLeadRepository;

    @Autowired
    ObjectMapperUtils objectMapperUtils;
    @Override
    public Page<LeadTenant> findAllLeads(Pageable pageable) {
        return tenantLeadRepository.findAll(pageable);
    }

    @Override
    public void saveAndChangeLeadTenantStatus(String leadFinalStatus, LeadTenant leadTenant) {
        if (leadFinalStatus == null || leadTenant == null) {
            throw new IllegalArgumentException("Lead final status or LeadTenant cannot be null");
        }

        // Switch-case is more readable than multiple if-else
        switch (leadFinalStatus) {
            case "CALL_BACK_LATER":
                leadTenant.setLeadStatus(LeadStatus.CALL_BACK_LATER);
                break;
            case "CUSTOMER_AGREED":
                leadTenant.setLeadStatus(LeadStatus.CUSTOMER_AGREED);
                break;
            case "VISIT_SCHEDULED":
                leadTenant.setLeadStatus(LeadStatus.VISIT_SCHEDULED);
                break;
            case "REJECTED":
                leadTenant.setLeadStatus(LeadStatus.REJECTED);
                break;
            case "INVALID_LEAD":
                leadTenant.setLeadStatus(LeadStatus.INVALID_LEAD);
                break;
            case "ONBOARDED":
                leadTenant.setLeadStatus(LeadStatus.ONBOARDED);
                break;
            default:
                leadTenant.setLeadStatus(LeadStatus.OTHERS);
                break;
        }

        log.info("tenant lead status updated to: {}", leadTenant.getLeadStatus());
        tenantLeadRepository.save(leadTenant);
    }



    public LeadTenant addTenantLead(TenantLeadDTO newLeadTenantDTO) {

        return null;
    }




}
