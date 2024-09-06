package com.houseclay.zebra.service;

import com.houseclay.zebra.model.lead.LeadBuyer;
import com.houseclay.zebra.model.lead.LeadOwner;

public interface BuyerLeadService {
    void saveAndChangeLeadBuyerStatus(String leadFinalStatus, LeadBuyer leadBuyer);
}
