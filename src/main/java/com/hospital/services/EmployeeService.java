package com.hospital.services;

import com.hospital.dto.EmployeeRequest;
import com.hospital.dto.EmployeeResponse;
import com.hospital.dto.LevelRequest;
import com.hospital.dto.LevelResponse;
import com.hospital.entities.Employee;
import com.hospital.entities.Level;
import com.hospital.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<EmployeeResponse> getEmployeeList() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(this::mapToEmployeeResponse).collect(Collectors.toList());
    }

    public EmployeeResponse getEmployeeById(String id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        Employee employee = employeeOptional.orElse(null);
        if(employee == null){
            return null;
        }
        return this.mapToEmployeeResponse(employee);
    }

    public EmployeeResponse createNewEmployee(EmployeeRequest employeeRequest) {
        Employee employee = this.mapToEmployeeObject(employeeRequest);
        employeeRepository.save(employee);
        log.info("Employee ID {" + employee.getId() + "} is saved");
        return this.mapToEmployeeResponse(employee);
    }
    public void deleteEmployeeById(String id) {
        employeeRepository.deleteById(id);
        log.info("Employee ID {" + id + "} is deleted");
    }

    public void updateEmployeeStatusById(EmployeeRequest employeeRequest) {
        Employee employee = this.mapToEmployeeObject(employeeRequest);
        employeeRepository.save(employee);
        log.info("Employee ID {" + employee.getId() +"} is updated");
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        LevelResponse lr = new LevelResponse();
        lr.setId(employee.getLevel().getId());
        lr.setName(employee.getLevel().getName());
        lr.setStatus(employee.getLevel().isStatus());
        lr.setDateImported(employee.getLevel().getDateImported());
        lr.setDateModified(employee.getLevel().getDateModified());
        lr.setDescription(employee.getLevel().getDescription());

        return EmployeeResponse.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .description(employee.getDescription())
                .status(employee.isStatus())
                .address(employee.getAddress())
                .user(employee.getUser())
                .dateModified(employee.getDateModified())
                .dateImported(employee.getDateImported())
                .level(lr)
                .build();
    }

    private Employee mapToEmployeeObject(EmployeeRequest employeeRequest) {
        Level l = new Level();
        l.setId(employeeRequest.getLevel().getId());
        l.setName(employeeRequest.getLevel().getName());
        l.setStatus(employeeRequest.getLevel().isStatus());
        l.setDateImported(employeeRequest.getLevel().getDateImported());
        l.setDateModified(employeeRequest.getLevel().getDateModified());
        l.setDescription(employeeRequest.getLevel().getDescription());

        return Employee.builder()
                .id(UUID.randomUUID().toString())
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .email(employeeRequest.getEmail())
                .description(employeeRequest.getDescription())
                .status(employeeRequest.isStatus())
                .address(employeeRequest.getAddress())
                .dateImported(employeeRequest.getDateImported())
                .dateModified(employeeRequest.getDateModified())
                .user(employeeRequest.getUser())
                .level(l)
                .build();
    }
}
