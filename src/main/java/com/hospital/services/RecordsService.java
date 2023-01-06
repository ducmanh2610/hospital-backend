package com.hospital.services;

import com.hospital.dto.RecordsRequest;
import com.hospital.dto.RecordsResponse;
import com.hospital.entities.Records;
import com.hospital.repositories.RecordsRepository;
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
public class RecordsService {
    private final RecordsRepository recordsRepository;

    public List<RecordsResponse> getRecordsList() {
        List<Records> recordss = recordsRepository.findAll();
        return recordss.stream().map(this::mapToRecordsResponse).collect(Collectors.toList());
    }

    public RecordsResponse getRecordsById(String id) {
        Optional<Records> recordsOptional = recordsRepository.findById(id);
        Records records = recordsOptional.orElse(null);
        return this.mapToRecordsResponse(records);
    }

    public RecordsResponse createNewRecords(RecordsRequest recordsRequest) {
        Records records = this.mapToRecordsObject(recordsRequest);
        recordsRepository.save(records);
        log.info("Records ID {" + records.getId() + "} is saved");
        return this.mapToRecordsResponse(records);
    }
    public void deleteRecordsById(String id) {
        recordsRepository.deleteById(id);
        log.info("Records ID {" + id + "} is deleted");
    }

    public void updateRecordsStatusById(RecordsRequest recordsRequest) {
        Records records = this.mapToRecordsObject(recordsRequest);
        recordsRepository.save(records);
        log.info("Records ID {" + records.getId() +"} is updated");
    }

    private RecordsResponse mapToRecordsResponse(Records records) {
        return RecordsResponse.builder()
                .id(records.getId())
                .title(records.getTitle())
                .employee(records.getEmployee())
                .patient(records.getPatient())
                .status(records.isStatus())
                .build();
    }

    private Records mapToRecordsObject(RecordsRequest recordsRequest) {
        return Records.builder()
                .id(recordsRequest.getId())
                .title(recordsRequest.getTitle())
                .employee(recordsRequest.getEmployee())
                .patient(recordsRequest.getPatient())
                .status(recordsRequest.isStatus())
                .build();
    }
}
