package com.houseclay.zebra.service.impl;

import com.houseclay.zebra.model.lead.LeadBuyer;
import com.houseclay.zebra.model.lead.LeadOwner;
import com.houseclay.zebra.model.lead.enums.LeadStatus;
import com.houseclay.zebra.repository.BuyerLeadRepository;
import com.houseclay.zebra.service.BuyerLeadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerLeadServiceImpl implements BuyerLeadService {

    @Autowired private BuyerLeadRepository buyerLeadRepository;

    @Override
    public void saveAndChangeLeadBuyerStatus(String leadFinalStatus, LeadBuyer leadBuyer) {

            if (leadFinalStatus == null || leadBuyer == null) {
                throw new IllegalArgumentException("Lead final status or LeadBuyer cannot be null");
            }


            switch (leadFinalStatus) {
                case "CALL_BACK_LATER":
                    leadBuyer.setLeadStatus(LeadStatus.CALL_BACK_LATER);
                    break;
                case "CUSTOMER_AGREED":
                    leadBuyer.setLeadStatus(LeadStatus.CUSTOMER_AGREED);
                    break;
                case "VISIT_SCHEDULED":
                    leadBuyer.setLeadStatus(LeadStatus.VISIT_SCHEDULED);
                    break;
                case "REJECTED":
                    leadBuyer.setLeadStatus(LeadStatus.REJECTED);
                    break;
                case "INVALID_LEAD":
                    leadBuyer.setLeadStatus(LeadStatus.INVALID_LEAD);
                    break;
                case "ONBOARDED":
                    leadBuyer.setLeadStatus(LeadStatus.ONBOARDED);
                    break;
                default:
                    leadBuyer.setLeadStatus(LeadStatus.OTHERS);
                    break;
            }

            log.info("buyer lead status updated to: {}", leadBuyer.getLeadStatus());
            buyerLeadRepository.save(leadBuyer);
        }


}
