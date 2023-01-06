package com.hospital.controllers;

import com.hospital.dto.UserRequest;
import com.hospital.dto.UserResponse;
import com.hospital.dto.UserRequest;
import com.hospital.entities.User;
import com.hospital.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> listAll() {
        List<UserResponse> l = userService.getUserList();
        return ResponseEntity.ok(l);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable String id) {
        UserResponse ur = userService.getUserById(id);
        return ResponseEntity.ok(ur);
    }
    @PostMapping("/new")
    public ResponseEntity<UserResponse> createNew(@RequestBody UserRequest userRequest) {
        UserResponse ur = userService.createNewUser(userRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(ur.getId()).toUri();
        return  ResponseEntity.created(location).body(ur);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        UserResponse ur = userService.getUserById(id);
        if(ur != null) {
            userService.deleteUserById(id);
            return ResponseEntity.ok("User ID {"+ ur.getId() + "} is deleted");
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateById(@RequestBody UserRequest userRequest) {
        UserResponse ur = userService.getUserById(userRequest.getId());
        if(ur != null) {
            userService.updateUserStatusById(userRequest);
        }
        return ResponseEntity.notFound().build();
    }
}
