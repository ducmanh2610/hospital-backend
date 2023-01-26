package com.hospital.services;

import com.hospital.dto.LevelRequest;
import com.hospital.dto.MessageResponse;
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

    public List<Level> getLevelList() {
        return levelRepository.findAll();
    }

    public Level getLevelById(String id) {
        Optional<Level> ol = levelRepository.findById(id);
        return ol.orElse(null);
    }

    public MessageResponse createNewLevel(LevelRequest lr) {
        Level level = levelRepository.findByName(lr.getName()).orElse(null);
        if(level != null){
            return new MessageResponse("Level name " + lr.getName() + " already exist", 302);
        }
        Level l = levelRepository.save(this.mapToNewObject(lr));
        log.info("Level ID {" + l.getId() + "} is saved");
        return new MessageResponse("Level ID {" + l.getId() + "} is created", 201);
    }
    public boolean deleteLevelById(String id) {
        Optional<Level> ol = levelRepository.findById(id);
        Level l = ol.orElse(null);
        if(l == null) {
            log.info("Level ID {" + id + "} not found");
            return false;
        }
        levelRepository.delete(l);
        log.info("Level ID {" + id + "} is deleted");
        return true;
    }

    public boolean updateLevelById(LevelRequest lr) {
        Optional<Level> ol = levelRepository.findById(lr.getId());
        Level l = ol.orElse(null);
        if(l == null){
            return false;
        }
        Level updateLevel = this.mapToNewObject(lr);
        updateLevel.setId(l.getId());
        levelRepository.save(updateLevel);
        log.info("Level ID {" + l.getId() +"} is updated");
        return true;
    }

    private Level mapToNewObject(LevelRequest lr) {
        return Level.builder()
                .id(UUID.randomUUID().toString())
                .name(lr.getName())
                .status(lr.isStatus())
                .description(lr.getDescription())
                .dateImported(new Date())
                .dateModified(new Date())
                .build();
    }
}
