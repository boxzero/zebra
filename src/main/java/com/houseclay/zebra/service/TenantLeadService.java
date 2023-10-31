package com.houseclay.zebra.service;

import com.houseclay.zebra.dto.NewLeadTenantDTO;
import com.houseclay.zebra.model.lead.LeadTenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface TenantLeadService {

    Page<LeadTenant> findAllLeads(Pageable pageable);
    LeadTenant addTenantLead(NewLeadTenantDTO newLeadTenantDTO);

}
