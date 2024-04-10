package com.npt.anis.ANIS.lecture.service;

import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.domain.entity.Lecture;
import com.npt.anis.ANIS.lecture.domain.entity.LecturePreset;
import com.npt.anis.ANIS.lecture.domain.mapper.LectureMapper;
import com.npt.anis.ANIS.lecture.exception.NoSearchLectureList;
import com.npt.anis.ANIS.lecture.exception.NotFoundLectureException;
import com.npt.anis.ANIS.lecture.exception.NotFoundLecturePresetException;
import com.npt.anis.ANIS.lecture.repository.LecturePresetRepository;
import com.npt.anis.ANIS.lecture.repository.LectureRepository;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService{
    private final LectureRepository lectureRepository;
    private final LectureMapper lectureMapper;
    private final LecturePresetRepository lecturePresetRepository;
    /***
     *
     * @param lectureDto dto 를 받아와서 mapper를 사용하여 lecture 생성
     * @return
     */
    @Override
    public Lecture createLecture(LectureDto lectureDto){
        Lecture newLecture = lectureMapper.toEntity(lectureDto);
        return lectureRepository.save(newLecture);
    }
    /***
     *
     * @return 현재 데이베이스에 있는 모든 강의 리스트를 반환합니다
     */
    @Override
    public List<Lecture> showLectureList(){
        List<Lecture> lectureList = lectureRepository.findAll();
        return lectureList;
    }

    /***
     * @return preset 에 속해있지 않은 강의리스트를 반환
     */
    @Override
    public List<Lecture> showNoPresetLectureList(){
        List<Lecture> showNoPresetLectureList = new ArrayList<>();
        List<Lecture> lectureList = showLectureList();
        for (Lecture lecture: lectureList) {
            if (lecture.getLpIndex() > 0) {
                showNoPresetLectureList.add(lecture);
            }
        }
        return showNoPresetLectureList;
    }
    /***
     *
     * @param lecId 업데이트할 강의를 선택하기위해 Lecture의 pk 를 갖고옴
     * @param updateLectureDto 해당 변경사항을 감지하기 위해 LectureDto 를 통해 받아옴
     * @return 바뀐 Lecture 객체를 반환한다
     * 연관관계가 있었다면 해당 객체를 삭제하고 재 등록 해주었을텐데 변경사항만 감지하여 Lecture의 pk를
     * 최대한 건들지 않는쪽으로 코딩했습니다
     */
    @Override
    public Lecture updateLecture(long lecId,LectureDto updateLectureDto){
        Lecture lecture = lectureRepository.findById(lecId)
                .orElseThrow(() -> new NotFoundLectureException("Lecture not found"));
        // mapper를 사용해서 최대한 은닉화 하였습니다
        lectureMapper.updateFromDto(updateLectureDto,lecture);
        return lecture;
    }
    /***
     *
     * @param lecId 삭제할 lecture id 를 받아와서 삭제함
     * @return 삭제되었는지 안되었는지 boolean 으로 판단
     */
    @Override
    public boolean deleteLecture(long lecId){
        try {
        Lecture lecture = lectureRepository.findById(lecId)
                .orElseThrow(() -> new NotFoundLectureException("Lecture not found"));
            lectureRepository.delete(lecture);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /***
     * lecturePreset 찾기
     * @param lpIndex
     * @return
     */
    @Override
    public List<Lecture> findLecturePreset(long lpIndex){
        return Optional.ofNullable(lectureRepository.findAllBylpIndex(lpIndex))
                .orElseThrow(() -> new NoSearchLectureList("Lecture list is empty for lpIndex: " + lpIndex));
    }
    /***
     * preset과 lecture 을 join 해서 해당 preset 의 pk 를 fk 로 갖고있는 lecture의 List를 반환
     * @param lpIndex presetIndex를 받음
     * @return
     */
    @Override
    public List<Lecture> getLectureListByPreset(long lpIndex){
        List<Tuple> tupleList = lectureRepository.getByLecturePresetWithLecture(lpIndex);
        List<Lecture> lectureList = new ArrayList<>();
        // Lecture 클래스타입으로 가공
        for (Tuple tuple : tupleList) {
            Lecture lecture = tuple.get("lecture", Lecture.class);
            lectureList.add(lecture);
        }
        return lectureList;
    }
    /***
     * 프리셋의 들어가는 강의를 수정하는 메서드
     * 시간표에 있는 모든 강의들은 Lecture객체로 생각하고 메서드를 만듬
     * @param lpIndex preset안에 강의들을 수정하기위해서 해당 preset 의 pk를 받아옴
     * @param newLectureList 수정할 강의 목록
     * @return 수정할 강의 목록들 List로 반환
     */
    public List<Lecture> updateLecturePresetOfLectureList(Long lpIndex,List<Lecture> newLectureList){
        // 바꾸려는 LecturePreset이 있는지 확인
        lecturePresetRepository.findById(lpIndex).orElseThrow(()-> new NotFoundLecturePresetException("Not Found LecturePreset"));
        List<Lecture> lectureList = getLectureListByPreset(lpIndex);
        // 수정된 Lecture의 lpIndex 는 0으로 지정
        for (Lecture lecture: lectureList) {
            lecture.setLpIndex(0);
        }
        for (Lecture lecture: newLectureList) {
            lecture.setLpIndex(lpIndex);
        }
        return newLectureList;
    }

    /***
     * 현재 수강 가능한 수강신청 목록들을 갖고오는 메서드(현재 날짜의 년도와 학기에따라 달라짐)
     * 2024년 1학기면 Lecture 데이터의 lecYear 가 2024년 , semester가 1인 LectureList를 반환
     * @return 현재수강가능한 LectureList반환
     */
    public List<Lecture> showAvailableLectureList(){
        List<Lecture> lectureList = lectureRepository.findAll();
        List<Lecture> availableLectureList = new ArrayList<>();
        for (Lecture lecture : lectureList) {
            if(isCurrentSemester(lecture.getLecSemester(),lecture.getLecYear())){
                availableLectureList.add(lecture);
            }
        }
        return availableLectureList;
    }

    /***
     * 현재 몇학기인지 반환하는 메서드
     * @return 1학기면 1 2학기면 2 반환
     */
    private int getCurrentSemester() {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        //
        if (month >= 3 && month <= 7) {
            return 1;
        } else {
            return 2;
        }
    }

    /***
     * 현재년도 갖고오는 메서드
     * @return 현재년도로 정수값 반환
     */
    private int getCurrentYear() {
        LocalDate now = LocalDate.now();
        return now.getYear();
    }

    /***
     * 현재년도와 학기인지 아닌지 검증하는
     * @param semester 학기
     * @param year 년도
     * @return 현재년도와 학기가 맞으면 true 아니면 false 반환
     */
    private boolean isCurrentSemester(int semester, int year) {
        int currentSemester = getCurrentSemester();
        int currentYear = getCurrentYear();
        return semester == currentSemester && year == currentYear;
    }
}

