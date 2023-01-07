package com.hospital.controllers;

import com.hospital.dto.LevelRequest;
import com.hospital.entities.Level;
import com.hospital.services.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/level")
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
    public ResponseEntity<Level> createNew(@RequestBody LevelRequest levelRequest) {
        Level l = levelService.createNewLevel(levelRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(l.getId()).toUri();
        return  ResponseEntity.created(location).body(l);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        boolean isDeleted = levelService.deleteLevelById(id);
        if(isDeleted) {
            return ResponseEntity.ok("Level ID: " + id + " is deleted");
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateById(@RequestBody LevelRequest lr, @PathVariable String id) {
    boolean isUpdated = levelService.updateLevelById(lr, id);
    if(isUpdated) {
        return ResponseEntity.ok("Level ID " + id + " is updated");
    }
    return ResponseEntity.notFound().build();
    }
}
