package com.hospital.controllers;

import com.hospital.dto.RolesRequest;
import com.hospital.entities.Role;
import com.hospital.services.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/v1/roles")
@RolesAllowed("ROLE_ADMIN")
public class RolesController {
    @Autowired
    private RolesService rolesService;

    @GetMapping("/list")
    public ResponseEntity<List<Role>> getRolesList(){
        List<Role> roles = rolesService.getRolesList();
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/new")
    public ResponseEntity<Role> createNewRoles(@RequestBody RolesRequest rolesRequest){
        Role roles = rolesService.createNewRoles(rolesRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(roles.getId()).toUri();
        return ResponseEntity.created(location).body(roles);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRolesById(@PathVariable String id){
        rolesService.deleteRolesById(id);
        return ResponseEntity.status(200).body("Roles ID {" + id + "} has been deleted");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRolesById(@PathVariable String id){
        Role r = rolesService.findRolesById(id);
        return ResponseEntity.ok(r);
    }
}
