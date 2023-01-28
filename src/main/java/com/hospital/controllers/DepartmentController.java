package com.hospital.controllers;

import com.hospital.dto.DepartmentRequest;
import com.hospital.dto.MessageResponse;
import com.hospital.entities.Department;
import com.hospital.services.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/department")
@Slf4j
public class DepartmentController {
    @Autowired
    private DepartmentService deptService;

    public final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @PostMapping("/new")
    public ResponseEntity<String> createNew(@RequestBody DepartmentRequest deptReq) {
        MessageResponse msg = deptService.createNewDept(deptReq);
        logger.info(msg.getMessage());
        return ResponseEntity.status(msg.getStatus()).body(msg.getMessage());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getById(@PathVariable Long id) {
        Department dept = deptService.getDeptById(id);
        if (dept != null) {
            logger.info("Dept {"+ dept.getId().toString() + "}" + " was found");
            return ResponseEntity.status(200).body(dept);
        }
        logger.info("Dept {"+ id.toString() + "}" + "is not found");
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<Department>> listAll() {
        List<Department> deptList = deptService.getDeptList();
        return ResponseEntity.ok(deptList);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody DepartmentRequest deptReq) {
        Department updateDept = deptService.updateDeptById(deptReq);
        if (updateDept != null) {
            logger.info("Dept {"+ updateDept.getId().toString() + "}" + "is updated");
            return ResponseEntity.ok("Department {" + deptReq.getId() + "} is updated");

        }
        logger.error("Dept ID {" +deptReq.getId().toString() + "} is not updated");
        return ResponseEntity.status(500).body("Update Failed");
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        boolean isDeleted = deptService.deleteDeptById(id);
        if(isDeleted){
            logger.info("Dept ID {"+ id.toString() + "} is deleted");
            return ResponseEntity.status(204).body("Dept ID {"+ id + "} is deleted");
        }
        logger.info("Dept ID {" + id.toString() + " is not found");
        return ResponseEntity.status(404).body("Dept ID not found");
    }
}
