package com.npt.anis.ANIS.lecture.service;

import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.domain.entity.Lecture;
import com.npt.anis.ANIS.lecture.domain.entity.LecturePreset;
import com.npt.anis.ANIS.lecture.domain.mapper.LectureMapper;
import com.npt.anis.ANIS.lecture.exception.NoSearchLectureList;
import com.npt.anis.ANIS.lecture.exception.NotFoundLectureException;
import com.npt.anis.ANIS.lecture.repository.LecturePresetRepository;
import com.npt.anis.ANIS.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService{
    private final LectureRepository lectureRepository;
    private final LectureMapper lectureMapper;
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
     * @return 모든 강의 리스트를 반환합니다
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
    @Override
    public List<Lecture> findLecturePreset(long lpIndex){
        return Optional.ofNullable(lectureRepository.findAllBylpIndex(lpIndex))
                .orElseThrow(() -> new NoSearchLectureList("Lecture list is empty for lpIndex: " + lpIndex));
    }

}

