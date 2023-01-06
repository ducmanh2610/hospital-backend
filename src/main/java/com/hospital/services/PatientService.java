package com.hospital.services;

import com.hospital.dto.PatientRequest;
import com.hospital.dto.PatientResponse;
import com.hospital.entities.Patient;
import com.hospital.repositories.PatientRepository;
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
public class PatientService {
    private final PatientRepository patientRepository;

    public List<PatientResponse> getPatientList() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(this::mapToPatientResponse).collect(Collectors.toList());
    }

    public PatientResponse getPatientById(String id) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        Patient patient = patientOptional.orElse(null);
        return this.mapToPatientResponse(patient);
    }

    public PatientResponse createNewPatient(PatientRequest patientRequest) {
        Patient patient = this.mapToPatientObject(patientRequest);
        patientRepository.save(patient);
        log.info("Patient ID {" + patient.getId() + "} is saved");
        return this.mapToPatientResponse(patient);
    }
    public void deletePatientById(String id) {
        patientRepository.deleteById(id);
        log.info("Patient ID {" + id + "} is deleted");
    }

    public void updatePatientStatusById(PatientRequest patientRequest) {
        Patient patient = this.mapToPatientObject(patientRequest);
        patientRepository.save(patient);
        log.info("Patient ID {" + patient.getId() +"} is updated");
    }

    private PatientResponse mapToPatientResponse(Patient patient) {
        return PatientResponse.builder()
                .id(UUID.randomUUID().toString())
                .lastName(patient.getLastName())
                .firstName(patient.getFirstName())
                .email(patient.getEmail())
                .dateOfBirth(patient.getDateOfBirth())
                .address(patient.getAddress())
                .phoneNumber(patient.getPhoneNumber())
                .dateJoined(patient.getDateJoined())
                .dateCreated(patient.getDateCreated())
                .dateOut(patient.getDateOut())
                .status(patient.isStatus())
                .records(patient.getRecords())
                .build();
    }

    private Patient mapToPatientObject(PatientRequest patientRequest) {
        return Patient.builder()
                .id(UUID.randomUUID().toString())
                .lastName(patientRequest.getLastName())
                .firstName(patientRequest.getFirstName())
                .email(patientRequest.getEmail())
                .dateOfBirth(patientRequest.getDateOfBirth())
                .address(patientRequest.getAddress())
                .phoneNumber(patientRequest.getPhoneNumber())
                .dateJoined(patientRequest.getDateJoined())
                .dateCreated(patientRequest.getDateCreated())
                .dateOut(patientRequest.getDateOut())
                .status(patientRequest.isStatus())
                .records(patientRequest.getRecords())
                .build();
    }
}
