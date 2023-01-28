package com.hospital.services;

import com.hospital.dto.EmployeeRequest;
import com.hospital.entities.Employee;
import com.hospital.entities.Level;
import com.hospital.repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
@Transactional
public class EmployeeService {
    @Autowired private EmployeeRepository empRepo;
    public List<Employee> getEmployeeList() {
        return empRepo.findAll();
    }

    public Employee getEmployeeById(String id) {
        Optional<Employee> oe = empRepo.findById(id);
        return oe.orElse(null);
    }

    public Employee createNewEmployee(EmployeeRequest er) {
        Employee e = empRepo.save(this.mapToEmployeeObject(er));
        log.info("Employee ID {" + e.getId() + "} is saved");
        return e;
    }
    public boolean deleteEmployeeById(String id) {
        Optional<Employee> ol = empRepo.findById(id);
        Employee l = ol.orElse(null);
        if(l == null) {
            log.info("Employee ID {" + id + "} not found");
            return false;
        }
        empRepo.delete(l);
        log.info("Employee ID {" + id + "} is deleted");
        return true;
    }

    public boolean updateEmployeeById(EmployeeRequest er, String id) {
        Optional<Employee> oe = empRepo.findById(id);
        Employee e = oe.orElse(null);
        if(e == null){
            return false;
        }
        empRepo.saveAndFlush(this.mapToEmployeeObject(er));
        log.info("Employee ID {" + e.getId() +"} is updated");
        return true;
    }
    
    private Employee mapToEmployeeObject(EmployeeRequest er) {

        return Employee.builder()
                .id(UUID.randomUUID().toString())
                .firstName(er.getFirstName())
                .lastName(er.getLastName())
                .description(er.getDescription())
                .status(er.isStatus())
                .address(er.getAddress())
                .email(er.getEmail())
                .dateImported(new Date())
                .dateModified(new Date())
                .build();
    }
}
