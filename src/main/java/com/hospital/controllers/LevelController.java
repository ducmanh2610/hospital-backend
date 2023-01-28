package com.hospital.controllers;

import com.hospital.dto.LevelRequest;
import com.hospital.dto.MessageResponse;
import com.hospital.entities.Level;
import com.hospital.services.LevelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;

@RolesAllowed({"ROLE_ADMIN", "ROLE_MODERATOR"})
@RestController
@RequestMapping("/api/v1/level")
@Slf4j
public class LevelController {
    @Autowired
    private LevelService levelService;

    @GetMapping("/list")
    public ResponseEntity<List<Level>> listAll() {
        List<Level> ll = levelService.getLevelList();
        return ResponseEntity.ok(ll);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Level> getById(@PathVariable String id) {
        Level l = levelService.getLevelById(id);
        return ResponseEntity.ok(l);
    }
    @PostMapping("/new")
    public ResponseEntity<String> createNew(@RequestBody LevelRequest levelRequest) {
        MessageResponse msg = levelService.createNewLevel(levelRequest);
        return ResponseEntity.status(msg.getStatus()).body(msg.getMessage());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        boolean isDeleted = levelService.deleteLevelById(id);
        if(isDeleted) {
            return ResponseEntity.ok("Level ID: " + id + " is deleted");
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateById(@RequestBody LevelRequest lr) {
    boolean isUpdated = levelService.updateLevelById(lr);
    if(isUpdated) {
        return ResponseEntity.ok("Level ID " + lr.getId() + " is updated");
    }
    return ResponseEntity.notFound().build();
    }
}
