package com.npt.anis.ANIS.lecture.domain.entity;

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
public class LectureRegistered {
    @Id
    private long rlIndex;
    // Lecture FK
    private long lecID;
    // Member FK
    private long memberID;

}
