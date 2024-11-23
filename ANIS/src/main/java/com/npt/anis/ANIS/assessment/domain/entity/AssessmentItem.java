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
public class AssessmentItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId; // 진단평가 항목 ID
    private Long assmtId; // 진단평가 ID
    private String assmtArea; // 진단 영역
    private String assmtQuestion; // 진단 문항

    @Builder
    public AssessmentItem(Long assmtId, String assmtArea, String assmtQuestion) {
        this.assmtId = assmtId;
        this.assmtArea = assmtArea;
        this.assmtQuestion = assmtQuestion;
    }
}
