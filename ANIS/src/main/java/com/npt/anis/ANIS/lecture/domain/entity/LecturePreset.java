package com.npt.anis.ANIS.lecture.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LecturePreset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lpIndex;
    private String presetName;
    @ElementCollection
    @CollectionTable(
            name = "lecture_list",
            joinColumns = @JoinColumn(name="presetName"))
    @OrderColumn(name="lecture_index")
    private List<Long> lectureList;

    /***
     * createLecturePreset 메서드를 사용할때 자동으로 인덱스를 만들어주는데 값을 넣을 필요가 없고
     * 굳이 mapper를 사용하기 싫어서 만든 생성자
     * @param presetName ex) A,B,C
     * @param lecture_list lecture index 에 대한 리스트
     */
    public LecturePreset(String presetName, List<Long> lecture_list) {
        this.presetName = presetName;
        this.lectureList = lecture_list;
    }
}
