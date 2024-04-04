package com.npt.anis.ANIS.nosql.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document( collection = "survey_result" )
public class SurveyResult {

    private List<Student> studentId;
    private String surveyId;
    private String response;

}
