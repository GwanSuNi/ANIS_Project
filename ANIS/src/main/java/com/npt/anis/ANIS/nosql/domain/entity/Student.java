package com.npt.anis.ANIS.nosql.domain.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document( collection = "student" )
public class Student {
    @Id
    private int studentID;
    @ElementCollection
    private List<String> doneSurveys;
    @ElementCollection
    private List<String> participatedSurveys;

}
