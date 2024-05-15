package com.npt.anis.ANIS.lecture.service;

import com.npt.anis.ANIS.jwt.util.DateUtils;
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

@RequiredArgsConstructor
@Service("lectureServiceImpl")
public class LectureServiceImpl implements LectureService{
    private final LectureRepository lectureRepository;
    private final LectureMapper lectureMapper;
    private final LecturePresetRepository lecturePresetRepository;

    @Override
    public void saveLecture(LectureDto lectureDto){
        Lecture newLecture = lectureMapper.toEntity(lectureDto);
        lectureRepository.save(newLecture);
    }
    /**
     * //TODO 값 검증 해야함
     * @param lectureDto dto 를 받아와서 mapper를 사용하여 lecture 생성
     * @return
     * lecture 는 등록할때 LecturePresetIndex 가 null 로 등록되고
     * 프리셋에 등록할때 해당 프리셋에대한 강의 데이터들이 다시생성되게 만들었다
     */
    @Override
    public LectureDto createLecture(LectureDto lectureDto){
        lectureDto.setLpIndex(null);
        saveLecture(lectureDto);
        return lectureDto;
    }
    /**
     *
     * @param lectureDto lecturePreset을 등록할때 lpIndex가 null이 아닌 lecture를 만듬
     * @return
     */
    @Override
    public LectureDto createPresetLecture(LectureDto lectureDto){
        saveLecture(lectureDto);
        return lectureDto;
    }
    /***
     * @param lecId 업데이트할 강의를 선택하기위해 Lecture의 pk 를 갖고옴
     * @param updateLectureDto 해당 변경사항을 감지하기 위해 LectureDto 를 통해 받아옴
     * @return 바뀐 Lecture 객체를 반환한다
     * 연관관계가 있었다면 해당 객체를 삭제하고 재 등록 해주었을텐데 변경사항만 감지하여 Lecture의 pk를
     * 최대한 건들지 않는쪽으로 코딩했습니다
     */
    @Override
    public LectureDto updateLecture(long lecId,LectureDto updateLectureDto){
        Lecture lecture = lectureRepository.findById(lecId)
                .orElseThrow(() -> new NotFoundLectureException("Lecture not found"));
        // mapper를 사용해서 최대한 은닉화 하였습니다
        lectureMapper.updateFromDto(updateLectureDto,lecture);
        lectureRepository.save(lecture);
        return updateLectureDto;
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
     *
     * @return 현재 데이베이스에 있는 모든 강의 리스트를 반환합니다
     */
    @Override
    public List<LectureDto> showLectureList(){
        List<Lecture> lectureList = lectureRepository.findAll();
        List<LectureDto> lectureDtoList = lectureMapper.toDtos(lectureList);
        return lectureDtoList;
    }

    /***
     * @return preset에 등록할 수 있는 Lecture 반환
     * lpIndex 에 null 값이 있는 데이터들의 LectureList 반환 년도에 상관없이
     */
    @Override
    public List<LectureDto> showNoPresetLectureList(){
        List<LectureDto> showNoPresetLectureList = new ArrayList<>();
        List<LectureDto> lectureList = showLectureList();
        for (LectureDto lectureDto: lectureList) {
            if (lectureDto.getLpIndex() == null ) {
                showNoPresetLectureList.add(lectureDto);
            }
        }
        return showNoPresetLectureList;
    }

    /***
     * lecturePreset 찾기
     * @param lpIndex
     * @return
     */
    @Override
    public List<LectureDto> findLecturePreset(long lpIndex){
        List<Lecture> lectureList = Optional.ofNullable(lectureRepository.findAllBylpIndex(lpIndex))
                .orElseThrow(() -> new NoSearchLectureList("Lecture list is empty for lpIndex: " + lpIndex));
        return lectureMapper.toDtos(lectureList);

    }
    /***
     * preset과 lecture 을 join 해서 해당 preset 의 pk 를 fk 로 갖고있는 lecture의 List를 반환
     * lecturePreset이 갖고있는 lectureList 반환
     * @param lpIndex presetIndex를 받음
     * @return
     */
    @Override
    public List<LectureDto> getLectureListByPreset(long lpIndex){
        List<Tuple> tupleList = lectureRepository.getByLecturePresetWithLecture(lpIndex);
        List<Lecture> lectureList = new ArrayList<>();
        // Lecture 클래스타입으로 가공
        for (Tuple tuple : tupleList) {
            Lecture lecture = tuple.get("lecture", Lecture.class);
            lectureList.add(lecture);
        }
        return lectureMapper.toDtos(lectureList);
    }
    /***
     * 프리셋의 들어가는 강의를 수정하는 메서드
     * 시간표에 있는 모든 강의들은 Lecture객체로 생각하고 메서드를 만듬
     * @param lpIndex preset안에 강의들을 수정하기위해서 해당 preset 의 pk를 받아옴
     * @param newLectureList 수정할 강의 목록
     * @return 수정할 강의 목록들 List로 반환
     */
    public List<LectureDto> updateLecturePresetOfLectureList(Long lpIndex,List<LectureDto> newLectureList){
        // 바꾸려는 LecturePreset이 있는지 확인
        lecturePresetRepository.findById(lpIndex).orElseThrow(()-> new NotFoundLecturePresetException("Not Found LecturePreset"));
        List<LectureDto> lectureList = getLectureListByPreset(lpIndex);

        // 수정된 LectureList는 모두 삭제
        for (Lecture lecture: lectureMapper.toEntities(lectureList)) {
            deleteLecture(lecture.getLecID());
        }
        for (Lecture lecture: lectureMapper.toEntities(newLectureList)) {
            createPresetLecture(lectureMapper.toDto(lecture));
        }
        return newLectureList;
    }

    /***
     * 현재 수강 가능한 수강신청 목록들을 갖고오는 메서드(현재 날짜의 년도와 학기에따라 달라짐)
     * 2024년 1학기면 Lecture 데이터의 lecYear 가 2024 , semester가 1인 LectureList를 반환
     * @return 현재수강가능한 LectureList반환
     */
    public List<LectureDto> showAvailableLectureList(){
        // lpIndex가 null 인 LectureDtoList를 받아옴
        List<LectureDto> lectureListDto = showNoPresetLectureList();
        List<LectureDto> availableLectureList = new ArrayList<>();
        for (LectureDto lectureDto : lectureListDto) {
            if(DateUtils.isCurrentSemester(lectureDto.getLecSemester(),lectureDto.getLecYear())){
                availableLectureList.add(lectureDto);
            }
        }
        return availableLectureList;
    }

    // 두 개의 LectureDto 객체의 시간이 겹치는지 여부를 확인하는 메서드
    public boolean areTimesOverlap(LectureDto lectureDto1, LectureDto lectureDto2) {
        // 첫 번째 강의의 종료 시간이 두 번째 강의의 시작 시간보다 이후이고,
        // 두 번째 강의의 종료 시간이 첫 번째 강의의 시작 시간보다 이후이면 겹침
        return lectureDto1.getLecTimeEnd().isAfter(lectureDto2.getLecTimeStart()) &&
                lectureDto2.getLecTimeEnd().isAfter(lectureDto1.getLecTimeStart());
    }
}

