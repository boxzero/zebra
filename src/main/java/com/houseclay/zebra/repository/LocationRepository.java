package com.houseclay.zebra.repository;

import com.houseclay.zebra.model.Configure.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
    public Optional<Location> findByCityAndLocationNameAndPinCode(String city, String locationName, String pincode);
}
