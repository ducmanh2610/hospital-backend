package com.hospital.repositories;

import com.hospital.entities.ToolkitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository

public interface ToolkitTypeRepository extends JpaRepository<ToolkitType, String> {

}
