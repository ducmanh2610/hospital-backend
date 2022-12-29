package com.hospital.repositories;

import com.hospital.entities.Toolkit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolkitRepository extends JpaRepository<Toolkit, String> {
}
