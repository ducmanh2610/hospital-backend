package com.hospital.controllers;

import com.hospital.dto.UserRequest;
import com.hospital.entities.User;
import com.hospital.exception.ResourceNotFoundException;
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
    public ResponseEntity<String> createNew(@RequestBody UserRequest ur) {
        User newUser = userService.createNewUser(ur);
        if (newUser == null) {
            return ResponseEntity.status(302).body("Username already exist");
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(ur.getId()).toUri();
        return ResponseEntity.created(location).body("User Created");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        User ur = userService.getUserById(id);
        if (ur != null) {
            userService.deleteUserById(id);
            return ResponseEntity.ok("User ID {" + ur.getId() + "} is deleted");
        }
        return ResponseEntity.status(404).body("User ID {" + ur.getId() + "} not found");
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody UserRequest ur) {
        User u = userService.getUserById(ur.getId());
        userService.updateUserStatusById(ur);
        return ResponseEntity.ok("User ID {" + ur.getId() + " is updated");
    }
}
