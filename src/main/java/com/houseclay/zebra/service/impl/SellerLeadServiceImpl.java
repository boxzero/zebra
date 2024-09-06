package com.houseclay.zebra.service.impl;

import com.houseclay.zebra.model.lead.LeadSeller;
import com.houseclay.zebra.model.lead.enums.LeadStatus;
import com.houseclay.zebra.repository.SellerLeadRepository;
import com.houseclay.zebra.service.SellerLeadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SellerLeadServiceImpl implements SellerLeadService {
    @Autowired private SellerLeadRepository sellerLeadRepository;

    @Override
    public void saveAndChangeLeadSellerStatus(String leadFinalStatus, LeadSeller leadSeller) {
        if (leadFinalStatus == null || leadSeller == null) {
            throw new IllegalArgumentException("Lead final status or LeadTenant cannot be null");
        }


        switch (leadFinalStatus) {
            case "CALL_BACK_LATER":
                leadSeller.setLeadStatus(LeadStatus.CALL_BACK_LATER);
                break;
            case "CUSTOMER_AGREED":
                leadSeller.setLeadStatus(LeadStatus.CUSTOMER_AGREED);
                break;
            case "VISIT_SCHEDULED":
                leadSeller.setLeadStatus(LeadStatus.VISIT_SCHEDULED);
                break;
            case "REJECTED":
                leadSeller.setLeadStatus(LeadStatus.REJECTED);
                break;
            case "INVALID_LEAD":
                leadSeller.setLeadStatus(LeadStatus.INVALID_LEAD);
                break;
            case "ONBOARDED":
                leadSeller.setLeadStatus(LeadStatus.ONBOARDED);
                break;
            default:
                leadSeller.setLeadStatus(LeadStatus.OTHERS);
                break;
        }

        log.info("seller lead status updated to: {}", leadSeller.getLeadStatus());
        sellerLeadRepository.save(leadSeller);

    }
}
