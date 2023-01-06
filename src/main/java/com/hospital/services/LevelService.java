package com.hospital.services;

import com.hospital.dto.LevelRequest;
import com.hospital.dto.LevelResponse;
import com.hospital.entities.Level;
import com.hospital.repositories.LevelRepository;
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
public class LevelService {
    private final LevelRepository levelRepository;

    public List<LevelResponse> getLevelList() {
        List<Level> levels = levelRepository.findAll();
        return levels.stream().map(this::mapToLevelResponse).collect(Collectors.toList());
    }

    public LevelResponse getLevelById(String id) {
        Optional<Level> levelOptional = levelRepository.findById(id);
        Level level = levelOptional.orElse(null);
        if(level == null) {
            return null;
        }
        return this.mapToLevelResponse(level);
    }

    public LevelResponse createNewLevel(LevelRequest levelRequest) {
        Level level = this.mapToLevelObject(levelRequest);
        levelRepository.save(level);
        log.info("Level ID {" + level.getId() + "} is saved");
        return this.mapToLevelResponse(level);
    }
    public void deleteLevelById(String id) {
        levelRepository.deleteById(id);
        log.info("Level ID {" + id + "} is deleted");
    }

    public void updateLevelStatusById(LevelRequest levelRequest) {
        Level level = this.mapToLevelObject(levelRequest);
        levelRepository.save(level);
        log.info("Level ID {" + level.getId() +"} is updated");
    }

    private LevelResponse mapToLevelResponse(Level level) {
        return LevelResponse.builder()
                .id(level.getId())
                .name(level.getName())
                .status(level.isStatus())
                .description(level.getDescription())
                .dateImported(level.getDateImported())
                .dateModified(level.getDateModified())
                .build();
    }

    private Level mapToLevelObject(LevelRequest levelRequest) {
        return Level.builder()
                .id(UUID.randomUUID().toString())
                .name(levelRequest.getName())
                .status(levelRequest.isStatus())
                .description(levelRequest.getDescription())
                .dateModified(new Date())
                .dateImported(new Date())
                .build();
    }
}
