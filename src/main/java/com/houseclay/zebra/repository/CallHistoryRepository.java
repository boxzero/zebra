package com.houseclay.zebra.repository;

import com.houseclay.zebra.model.lead.CallHistory;
import com.houseclay.zebra.model.lead.LeadOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CallHistoryRepository extends JpaRepository<CallHistory, UUID> {

}
