package com.hospital.services;

import com.hospital.dto.ToolkitRequest;
import com.hospital.dto.ToolkitResponse;
import com.hospital.entities.Toolkit;
import com.hospital.entities.ToolkitType;
import com.hospital.repositories.ToolkitRepository;
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
public class ToolkitService {
    private final ToolkitRepository toolkitRepository;

    public Toolkit createNewToolkit(ToolkitRequest toolkitRequest) {
        ToolkitType tt = toolkitRequest.getToolkitType();
        Toolkit toolkit = Toolkit.builder()
                .Id(UUID.randomUUID().toString())
                .name(toolkitRequest.getName())
                .description((toolkitRequest.getDescription()))
                .dateImported(new Date())
                .dateModified(new Date())
                .status(true)
                .imageURL(toolkitRequest.getImageURL())
                .toolkitType(tt)
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
        ToolkitType tt = new ToolkitType();
        tt.setId(toolkit.getToolkitType().getId());
        tt.setName(toolkit.getToolkitType().getName());
        tt.setDescription(toolkit.getToolkitType().getId());
        tt.setDateImported(toolkit.getToolkitType().getDateImported());
        tt.setDateModified(toolkit.getToolkitType().getDateModified());
        tt.setStatus(toolkit.getToolkitType().isStatus());
        tt.setImageURL(toolkit.getToolkitType().getImageURL());

        return ToolkitResponse.builder()
                .Id(toolkit.getId())
                .name(toolkit.getName())
                .description(toolkit.getDescription())
                .dateImported(toolkit.getDateImported())
                .dateModified(toolkit.getDateModified())
                .status(toolkit.isStatus())
                .toolkitType(tt)
                .build();
    }
}
