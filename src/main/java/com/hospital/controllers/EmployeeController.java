package com.hospital.controllers;

import com.hospital.dto.EmployeeRequest;
import com.hospital.dto.EmployeeRequest;
import com.hospital.entities.Employee;
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
    public ResponseEntity<List<Employee>> listAll() {
        List<Employee> le = employeeService.getEmployeeList();
        return ResponseEntity.ok(le);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable String id) {
        Employee l = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(l);
    }
    @PostMapping("/new")
    public ResponseEntity<Employee> createNew(@RequestBody EmployeeRequest er) {
        Employee e = employeeService.createNewEmployee(er);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(e.getId()).toUri();
        return  ResponseEntity.created(location).body(e);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        boolean isDeleted = employeeService.deleteEmployeeById(id);
        if(isDeleted) {
            return ResponseEntity.ok("Employee ID: " + id + " is deleted");
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateById(@RequestBody EmployeeRequest er, @PathVariable String id) {
        boolean isUpdated = employeeService.updateEmployeeById(er, id);
        if(isUpdated) {
            return ResponseEntity.ok("Employee ID " + id + " is updated");
        }
        return ResponseEntity.notFound().build();
    }
}
