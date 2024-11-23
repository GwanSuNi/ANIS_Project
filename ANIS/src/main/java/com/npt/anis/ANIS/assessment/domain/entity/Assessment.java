package com.npt.anis.ANIS.assessment.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assmtId; // 진단평가 ID
    private Long lecID; // 강의 ID
    private String assmtName; // 진단평가 이름
    private LocalDateTime startDate; // 진단평가 시작일
    private LocalDateTime endDate; // 진단평가 마감일

    @Builder
    public Assessment(Long lecID, String assmtName, LocalDateTime startDate, LocalDateTime endDate) {
        this.lecID = lecID;
        this.assmtName = assmtName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
