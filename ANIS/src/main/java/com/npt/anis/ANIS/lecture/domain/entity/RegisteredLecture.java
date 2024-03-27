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
public class RegisteredLecture {
    @Id
    private Long rlIndex;

    // private Long lecId;
    // erd에 대한 얘기가 끝나지 않아서 비워둠

    // private Long lecID;
    // private Long studentID;

}
