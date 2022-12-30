package com.hospital.controllers;

import com.hospital.dto.ToolkitRequest;
import com.hospital.dto.ToolkitResponse;
import com.hospital.entities.Toolkit;
import com.hospital.entities.ToolkitType;
import com.hospital.repositories.ToolkitRepository;
import com.hospital.services.ToolkitService;
import com.hospital.services.ToolkitTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.tools.Tool;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/toolkit")
public class ToolkitController {
    @Autowired
    private ToolkitService toolkitService;
    private ToolkitTypeService toolkitTypeService;

    @GetMapping("/list")
    public ResponseEntity<List<ToolkitResponse>> getToolkitList() {
        List<ToolkitResponse> toolkitList = toolkitService.getAllToolkitList();
        if(toolkitList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(toolkitList);
    }

    @PostMapping("/new")
    public ResponseEntity<Toolkit> addNewToolkit(@RequestBody ToolkitRequest toolkitRequest){
        Toolkit t = toolkitService.createNewToolkit(toolkitRequest);
        return ResponseEntity.ok(t);
    }
}
