package com.npt.anis.ANIS.assessment.repository;

import com.npt.anis.ANIS.assessment.domain.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
}
