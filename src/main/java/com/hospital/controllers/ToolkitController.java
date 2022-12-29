package com.hospital.controllers;

import com.hospital.dto.ToolkitRequest;
import com.hospital.dto.ToolkitResponse;
import com.hospital.entities.Toolkit;
import com.hospital.entities.ToolkitType;
import com.hospital.repositories.ToolkitRepository;
import com.hospital.services.ToolkitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.tools.Tool;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/toolkit")
public class ToolkitController {
    @Autowired
    private ToolkitService toolkitService;

    @GetMapping("/toolkit-list")
    public ResponseEntity<List<ToolkitResponse>> getToolkitList() {
        List<ToolkitResponse> toolkitList = toolkitService.getAllToolkitList();
        return ResponseEntity.ok(toolkitList);
    }

    @PostMapping("/new-toolkit")
    public ResponseEntity<Toolkit> addNewToolkit(@RequestBody ToolkitRequest toolkitRequest){
        Toolkit t = toolkitService.createNewToolkit(toolkitRequest);
        return ResponseEntity.ok(t);
    }
}
