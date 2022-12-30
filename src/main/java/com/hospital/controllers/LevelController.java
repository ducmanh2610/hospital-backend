package com.hospital.controllers;

import com.hospital.dto.LevelRequest;
import com.hospital.dto.LevelResponse;
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
    public ResponseEntity<List<LevelResponse>> listAll() {
        List<LevelResponse> l = levelService.getLevelList();
        return ResponseEntity.ok(l);
    }
    @GetMapping("/{id}")
    public ResponseEntity<LevelResponse> getById(@PathVariable String id) {
        LevelResponse lr = levelService.getLevelById(id);
        return ResponseEntity.ok(lr);
    }
    @PostMapping("/new")
    public ResponseEntity<LevelResponse> createNew(@RequestBody LevelRequest levelRequest) {
        LevelResponse lr = levelService.createNewLevel(levelRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(lr.getId()).toUri();
        return  ResponseEntity.created(location).body(lr);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        LevelResponse lr = levelService.getLevelById(id);
        if(lr != null) {
            levelService.deleteLevelById(id);
            return ResponseEntity.ok("Level ID {"+ lr.getId() + "} is deleted");
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateById(@RequestBody LevelRequest levelRequest) {
    LevelResponse lr = levelService.getLevelById(levelRequest.getId());
    if(lr != null) {
        levelService.updateLevelStatusById(levelRequest);
    }
    return ResponseEntity.notFound().build();
    }
}
