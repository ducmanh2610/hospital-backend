package com.hospital.controllers;

import com.hospital.dto.EmployeeRequest;
import com.hospital.dto.EmployeeResponse;
import com.hospital.entities.Employee;
import com.hospital.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/list")
    public ResponseEntity<List<EmployeeResponse>> listAll() {
        List<EmployeeResponse> l = employeeService.getEmployeeList();
        return ResponseEntity.ok(l);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getById(@PathVariable String id) {
        EmployeeResponse lr = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(lr);
    }
    @PostMapping("/new")
    public ResponseEntity<EmployeeResponse> createNew(@RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse lr = employeeService.createNewEmployee(employeeRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(lr.getId()).toUri();
        return  ResponseEntity.created(location).body(lr);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        EmployeeResponse er = employeeService.getEmployeeById(id);
        if(er != null) {
            employeeService.deleteEmployeeById(id);
            return ResponseEntity.ok("Employee ID {"+ er.getId() + "} is deleted");
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateById(@RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse lr = employeeService.getEmployeeById(employeeRequest.getId());
        if(lr != null) {
            employeeService.updateEmployeeStatusById(employeeRequest);
        }
        return ResponseEntity.notFound().build();
    }
}
