package com.houseclay.zebra.repository;

import com.houseclay.zebra.model.PropertyRent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PropertyForRentRepository extends JpaRepository<PropertyRent, UUID> {


}
