package com.hospital.controllers;

import com.hospital.dto.PatientRequest;
import com.hospital.dto.PatientResponse;
import com.hospital.entities.Patient;
import com.hospital.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @GetMapping("/list")
    public ResponseEntity<List<PatientResponse>> listAll() {
        List<PatientResponse> l = patientService.getPatientList();
        return ResponseEntity.ok(l);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getById(@PathVariable String id) {
        PatientResponse pr = patientService.getPatientById(id);
        return ResponseEntity.ok(pr);
    }
    @PostMapping("/new")
    public ResponseEntity<PatientResponse> createNew(@RequestBody PatientRequest patientRequest) {
        PatientResponse pr = patientService.createNewPatient(patientRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(pr.getId()).toUri();
        return  ResponseEntity.created(location).body(pr);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        PatientResponse pr = patientService.getPatientById(id);
        if(pr != null) {
            patientService.deletePatientById(id);
            return ResponseEntity.ok("Patient ID {"+ pr.getId() + "} is deleted");
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateById(@RequestBody PatientRequest patientRequest) {
        PatientResponse pr = patientService.getPatientById(patientRequest.getId());
        if(pr != null) {
            patientService.updatePatientStatusById(patientRequest);
        }
        return ResponseEntity.notFound().build();
    }
}
