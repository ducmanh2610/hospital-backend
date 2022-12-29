package com.hospital.controllers;

import com.hospital.dto.ToolkitTypeRequest;
import com.hospital.dto.ToolkitTypeResponse;
import com.hospital.entities.ToolkitType;
import com.hospital.services.ToolkitTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/toolkit-type")
public class ToolkitTypeController {

    @Autowired
    private ToolkitTypeService toolkitTypeService;
    @GetMapping("/toolkit-type-list")
    public ResponseEntity<List<ToolkitTypeResponse>> getAllToolkitType() {
        List<ToolkitTypeResponse> toolkitTypes = toolkitTypeService.getAllToolkitList();
        return ResponseEntity.ok(toolkitTypes);
    }

    @PostMapping("/add-new-toolkit-type")
    public ResponseEntity<ToolkitType> addNewToolkitType(@RequestBody ToolkitTypeRequest toolkitTypeRequest){
        ToolkitType tt = toolkitTypeService.createNewToolkitType(toolkitTypeRequest);
        return ResponseEntity.ok(tt);
    }
}
