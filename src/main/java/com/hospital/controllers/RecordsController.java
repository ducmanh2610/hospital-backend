package com.hospital.controllers;

import com.hospital.dto.RecordsRequest;
import com.hospital.dto.RecordsResponse;
import com.hospital.entities.Records;
import com.hospital.services.RecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/records")
public class RecordsController {
    @Autowired
    private RecordsService recordsService;
    @GetMapping("/list")
    public ResponseEntity<List<RecordsResponse>> listAll() {
        List<RecordsResponse> l = recordsService.getRecordsList();
        return ResponseEntity.ok(l);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RecordsResponse> getById(@PathVariable String id) {
        RecordsResponse lr = recordsService.getRecordsById(id);
        return ResponseEntity.ok(lr);
    }
    @PostMapping("/new")
    public ResponseEntity<RecordsResponse> createNew(@RequestBody RecordsRequest recordsRequest) {
        RecordsResponse lr = recordsService.createNewRecords(recordsRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(lr.getId()).toUri();
        return  ResponseEntity.created(location).body(lr);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        RecordsResponse lr = recordsService.getRecordsById(id);
        if(lr != null) {
            recordsService.deleteRecordsById(id);
            return ResponseEntity.ok("Records ID {"+ lr.getId() + "} is deleted");
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateById(@RequestBody RecordsRequest recordsRequest) {
        RecordsResponse lr = recordsService.getRecordsById(recordsRequest.getId());
        if(lr != null) {
            recordsService.updateRecordsStatusById(recordsRequest);
        }
        return ResponseEntity.notFound().build();
    }
}
