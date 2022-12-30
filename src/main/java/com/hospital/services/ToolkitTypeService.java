package com.hospital.services;

import com.hospital.dto.ToolkitTypeRequest;
import com.hospital.dto.ToolkitTypeResponse;
import com.hospital.entities.ToolkitType;
import com.hospital.repositories.ToolkitTypeRepository;
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
public class ToolkitTypeService {
    private final ToolkitTypeRepository toolkitTypeRepository;

    public ToolkitType createNewToolkitType(ToolkitTypeRequest toolkitTypeRequest) {
        ToolkitType toolkitType = ToolkitType.builder()
                .Id(UUID.randomUUID().toString())
                .name(toolkitTypeRequest.getName())
                .description((toolkitTypeRequest.getDescription()))
                .dateImported(new Date())
                .dateModified(new Date())
                .status(true)
                .imageURL(toolkitTypeRequest.getImageURL())
                .build();

        toolkitTypeRepository.save(toolkitType);
        log.info("ToolkitType ID {} " + toolkitType.getId() + "is saved !");
        return toolkitType;
    }

    public List<ToolkitTypeResponse> getAllToolkitList() {
        List<ToolkitType> toolkitTypes = toolkitTypeRepository.findAll();
        return toolkitTypes.stream().map(this::mapToToolkitTypeResponse).collect(Collectors.toList());
    }

    public Optional<ToolkitType> getToolkitTypeById(String toolkitTypeId) {
        return toolkitTypeRepository.findById(toolkitTypeId);
    }

    public void deleteToolkitTypeById(String toolkitTypeId){
        toolkitTypeRepository.deleteById(toolkitTypeId);
    }

    private ToolkitTypeResponse mapToToolkitTypeResponse(ToolkitType toolkitType) {
        return ToolkitTypeResponse.builder()
                .Id(toolkitType.getId())
                .name(toolkitType.getName())
                .description(toolkitType.getDescription())
                .dateImported(toolkitType.getDateImported())
                .dateModified(toolkitType.getDateModified())
                .status(toolkitType.isStatus())
                .build();
    }
}
