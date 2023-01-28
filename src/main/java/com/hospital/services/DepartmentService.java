package com.hospital.services;

import com.hospital.dto.DepartmentRequest;
import com.hospital.dto.MessageResponse;
import com.hospital.entities.Department;
import com.hospital.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository deptRepo;

    public MessageResponse createNewDept(DepartmentRequest deptReq) {
        Department dept = deptRepo.findByName(deptReq.getName())
                .orElse(null);
        if(dept == null){
            Department newDept = this.mapToDept(deptReq);
            Department savedDept = deptRepo.save(newDept);
            return new MessageResponse("Dept ID {" + savedDept.getId() + "} is created", 201);
        }
        return new MessageResponse("Dept name {" + deptReq.getName() + "} is exist", 302);
    }

    public Department getDeptById(Long deptId) {
        return deptRepo.findById(deptId).orElse(null);
    }

    public List<Department> getDeptList(){
        return deptRepo.findAll();
    }

    public Department updateDeptById(DepartmentRequest deptReq){
        Department dept = deptRepo.findById(deptReq.getId()).orElse(null);
        if(dept == null){
            return null;
        }
        Department updateDept = this.mapToDept(deptReq);
        updateDept.setId(deptReq.getId());
        return deptRepo.save(updateDept);
    }

    public boolean deleteDeptById(Long deptId){
        Department dept = deptRepo.findById(deptId).orElse(null);
        if(dept == null){
            return false;
        }
        deptRepo.delete(dept);
        return true;
    }

    private Department mapToDept(DepartmentRequest deptReq) {
        return Department.builder()
                .name(deptReq.getName())
                .description(deptReq.getDescription())
                .imageUrl(deptReq.getImageUrl())
                .status(deptReq.isStatus())
                .createdDate(new Date())
                .modifiedDate(new Date())
                .build();
    }
}
