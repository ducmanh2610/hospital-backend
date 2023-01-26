package com.hospital.controllers;

import com.hospital.dto.UserRequest;
import com.hospital.entities.User;
import com.hospital.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/v1/user")
@RolesAllowed("ROLE_ADMIN")
@CrossOrigin("http://localhost:4200")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/list")
    public ResponseEntity<List<User>> listAll() {
        List<User> l = userService.getUserList();
        return ResponseEntity.ok(l);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable String id) {
        User ur = userService.getUserById(id);
        return ResponseEntity.ok(ur);
    }
    @PostMapping("/new")
    public ResponseEntity<User> createNew(@RequestBody UserRequest ur) {
        User u = userService.createNewUser(ur);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(ur.getId()).toUri();
        return  ResponseEntity.created(location).body(u);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        User ur = userService.getUserById(id);
        if(ur != null) {
            userService.deleteUserById(id);
            return ResponseEntity.ok("User ID {"+ ur.getId() + "} is deleted");
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateById(@RequestBody UserRequest ur) {
        User u = userService.getUserById(ur.getId());
        if(u != null) {
            userService.updateUserStatusById(ur);
        }
        return ResponseEntity.notFound().build();
    }
}
