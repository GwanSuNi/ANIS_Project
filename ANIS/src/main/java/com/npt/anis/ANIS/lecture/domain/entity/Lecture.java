package com.npt.anis.ANIS.lecture.domain.entity;

import com.npt.anis.ANIS.global.util.DateUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lecID;
    /* 어드민에서 lecturePreset을 만들기위해서 lecturePreset에 등록되지않은
    lecture들의 목록들을 보여주기위해서 lpIndex 가 필요하다고 생각함
    */
    private Long lpIndex;
    private String lecName;
    // hour
    private int lecCredit;
    private int lecYear;
    private int lecSemester;
    private String lecProfessor;
    private String lecDay;
    private int lecGrade;
    private LocalTime lecTimeStart;
    private LocalTime lecTimeEnd;
    private String lectureRoom;

    /***
     * PrePersist의 단점은 코드가 JPA 에 너무 의존적이라는게 단점이다
     * PrePersist는 엔티티가 처음 저장될때 한번 일어나고 그 이후에는 일어나지 않는다
     * PrePersist를 사용하고싶지 않은 경우엔 먼저 save를 해서 엔티티를 저장한 후에 해당 값을 update 해주면 된다
     */
    @PrePersist
    public void prePersist() {
        this.lecSemester = DateUtils.getCurrentSemester();
        this.lecYear = DateUtils.getCurrentYear();
    }

}


