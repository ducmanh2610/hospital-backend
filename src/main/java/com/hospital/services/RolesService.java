package com.hospital.services;

import com.hospital.dto.RolesRequest;
import com.hospital.entities.Role;
import com.hospital.repositories.RolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RolesService {
    private final RolesRepository rolesRepository;

    public Role createNewRoles(RolesRequest rolesRequest) {
        Role roles = Role.builder()
                .id(UUID.randomUUID().toString())
                .name(rolesRequest.getName())
                .build();
        Role r = rolesRepository.save(roles);
        log.info("Roles ID {" + r.getId() + "} is Created");
        return r;
    }

    public List<Role> getRolesList() {
        return rolesRepository.findAll();
    }

    public void deleteRolesById(String id){
        rolesRepository.deleteById(id);
    }

    public Role findRolesById(String id) {
        Optional<Role> optionalRoles = rolesRepository.findById(id);
        return optionalRoles.orElse(null);
    }
}
