package com.npt.anis.ANIS.assessment.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AssessmentResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long arId; // 진단평가 결과 ID
    private Long itemId; // 진단평가 항목 ID
    private String studentID; // 학생 ID
    private int arScore; // 진단평가 항목의 점수

    @Builder
    public AssessmentResult(Long itemId, String studentID, int arScore) {
        this.itemId = itemId;
        this.studentID = studentID;
        this.arScore = arScore;
    }
}
