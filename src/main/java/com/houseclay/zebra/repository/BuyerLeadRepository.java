package com.houseclay.zebra.repository;

import com.houseclay.zebra.model.lead.LeadBuyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BuyerLeadRepository extends JpaRepository<LeadBuyer, UUID> {
}
