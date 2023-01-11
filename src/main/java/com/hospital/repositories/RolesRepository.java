package com.hospital.repositories;

import com.hospital.entities.ERole;
import com.hospital.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, String> {
    Optional<Roles> findByName(ERole name);
}
