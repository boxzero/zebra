package com.houseclay.zebra.service.impl;

import com.houseclay.zebra.exceptionHandling.IdNotFoundException;
import com.houseclay.zebra.model.rental.Owner;
import com.houseclay.zebra.repository.OwnerRepository;
import com.houseclay.zebra.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;
    @Override
    public Owner fetchOwner(UUID ownerId) throws IdNotFoundException {
        Optional<Owner> owner = ownerRepository.findById(ownerId);
        if(!owner.isPresent()){
            throw new  IdNotFoundException(ownerId, "owner id not found ! ");
        }
        return owner.get();

    }
}
