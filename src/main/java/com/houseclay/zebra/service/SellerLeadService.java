package com.houseclay.zebra.service;

import com.houseclay.zebra.model.lead.LeadSeller;

public interface SellerLeadService {
    void saveAndChangeLeadSellerStatus(String leadFinalStatus, LeadSeller leadSeller);
}
