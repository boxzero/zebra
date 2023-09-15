package com.houseclay.zebra.service;

import com.houseclay.zebra.model.lead.Lead;

import java.util.UUID;

public interface LeadService {

    Lead saveNewLead(Lead lead);

    void deleteLead(UUID leadUUID);

    void fetchLead(UUID leadUUID);


}
