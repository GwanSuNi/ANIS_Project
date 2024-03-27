package com.npt.anis.ANIS.nosql.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SurveyResult {
    @Id
    private String id;
    private String studentId;
    private String surveyId;
    private String response;

}
