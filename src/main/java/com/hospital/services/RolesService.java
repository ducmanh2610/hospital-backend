package com.hospital.services;

import com.hospital.dto.RolesRequest;
import com.hospital.dto.RolesResponse;
import com.hospital.entities.ERole;
import com.hospital.entities.Roles;
import com.hospital.repositories.RolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RolesService {
    private final RolesRepository rolesRepository;

    public Roles createNewRoles(RolesRequest rolesRequest) {
        Roles roles = Roles.builder()
                .id(UUID.randomUUID().toString())
                .name(ERole.ROLE_USER)
                .build();
        Roles r = rolesRepository.save(roles);
        log.info("Roles ID {" + r.getId() + "} is Created");
        return r;
    }

    public List<Roles> getRolesList() {
        return rolesRepository.findAll();
    }

    public void deleteRolesById(String id){
        rolesRepository.deleteById(id);
    }

    public Roles findRolesById(String id) {
        Optional<Roles> optionalRoles = rolesRepository.findById(id);
        return optionalRoles.orElse(null);
    }
}
