package com.houseclay.zebra.repository;

import com.houseclay.zebra.model.lead.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LeadRepository extends JpaRepository<Lead, UUID> {
}
