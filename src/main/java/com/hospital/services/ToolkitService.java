package com.hospital.services;

import com.hospital.dto.ToolkitRequest;
import com.hospital.dto.ToolkitResponse;
import com.hospital.entities.Toolkit;
import com.hospital.repositories.ToolkitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ToolkitService {
    private final ToolkitRepository toolkitRepository;

    public Toolkit createNewToolkit(ToolkitRequest toolkitRequest) {
        Toolkit toolkit = Toolkit.builder()
                .Id(UUID.randomUUID().toString())
                .name(toolkitRequest.getName())
                .description((toolkitRequest.getDescription()))
                .dateImported(new Date())
                .dateModified(new Date())
                .status(true)
                .imageURL(toolkitRequest.getImageURL())
                .build();

        Toolkit tk = toolkitRepository.save(toolkit);
        log.info("Toolkit ID {} " + toolkit.getId() + "is saved !");
        return tk;
    }

    public List<ToolkitResponse> getAllToolkitList() {
        List<Toolkit> toolkits = toolkitRepository.findAll();
        return toolkits.stream().map(this::mapToToolkitResponse).collect(Collectors.toList());
    }

    private ToolkitResponse mapToToolkitResponse(Toolkit toolkit) {
        return ToolkitResponse.builder()
                .Id(toolkit.getId())
                .name(toolkit.getName())
                .description(toolkit.getDescription())
                .dateImported(toolkit.getDateImported())
                .dateModified(toolkit.getDateModified())
                .status(toolkit.isStatus())
                .toolkitType(toolkit.getToolkitType())
                .build();
    }
}
