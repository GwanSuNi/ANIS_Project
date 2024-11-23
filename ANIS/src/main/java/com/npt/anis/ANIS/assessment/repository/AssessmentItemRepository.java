package com.npt.anis.ANIS.assessment.repository;

import com.npt.anis.ANIS.assessment.domain.entity.AssessmentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentItemRepository extends JpaRepository<AssessmentItem, Long> {
    List<AssessmentItem> findByAssmtId(Long assmtId);

    List<AssessmentItem> findAllByAssmtIdIn(List<Long> assmtIds);
}
