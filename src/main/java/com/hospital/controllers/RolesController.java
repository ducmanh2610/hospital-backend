package com.hospital.controllers;

import com.hospital.dto.RolesRequest;
import com.hospital.dto.RolesResponse;
import com.hospital.entities.Roles;
import com.hospital.services.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RolesController {
    @Autowired
    private RolesService rolesService;

    @GetMapping("/list")
    public ResponseEntity<List<RolesResponse>> getRolesList(){
        List<RolesResponse> roles = rolesService.getRolesList();
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/new")
    public ResponseEntity<Roles> createNewRoles(@RequestBody RolesRequest rolesRequest){
        Roles roles = rolesService.createNewRoles(rolesRequest);
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

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRolesStatusById(@RequestBody RolesRequest rolesRequest) {
       Roles r = rolesService.findRolesById(rolesRequest.getId());
        if(r != null){
            r.setStatus(rolesRequest.isStatus());
            rolesService.updateRolesById(r);
            return ResponseEntity.ok().body("Roles ID {" + rolesRequest.getId() + "has been updated");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Roles> getRolesById(@PathVariable String id){
        Roles r = rolesService.findRolesById(id);
        return ResponseEntity.ok(r);
    }
}
