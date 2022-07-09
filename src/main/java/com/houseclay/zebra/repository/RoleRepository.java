package com.houseclay.zebra.repository;

import com.houseclay.zebra.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    public Role findByName(String rolename);
}
