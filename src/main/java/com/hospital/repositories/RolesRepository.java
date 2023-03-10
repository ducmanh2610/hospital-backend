package com.hospital.repositories;

import com.hospital.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(String name);
}
