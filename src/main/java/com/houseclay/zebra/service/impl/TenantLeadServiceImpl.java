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


    public LeadTenant addTenantLead(TenantLeadDTO newLeadTenantDTO) {

        return null;
    }


}
