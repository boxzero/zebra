package com.houseclay.zebra.service.impl;

import com.houseclay.zebra.model.lead.Lead;
import com.houseclay.zebra.repository.LeadRepository;
import com.houseclay.zebra.service.LeadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
public class LeadServiceImpl implements LeadService {

    @Autowired
    LeadRepository leadRepository;

    @Override
    public Lead saveNewLead(Lead lead) {
        return leadRepository.save(lead);
    }

    @Override
    public void deleteLead(UUID leadUUID) {

    }

    @Override
    public void fetchLead(UUID leadUUID) {

    }
}
