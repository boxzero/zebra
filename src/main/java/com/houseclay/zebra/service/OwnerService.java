package com.houseclay.zebra.service;

import com.houseclay.zebra.exceptionHandling.IdNotFoundException;
import com.houseclay.zebra.model.rental.Owner;

import java.util.UUID;

public interface OwnerService {
       Owner fetchOwner(UUID ownerId) throws IdNotFoundException;
}
