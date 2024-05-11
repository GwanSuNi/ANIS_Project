package com.npt.anis.ANIS.lecture.service;

import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.domain.dto.LectureRegisteredDto;
import com.npt.anis.ANIS.lecture.domain.entity.Lecture;
import com.npt.anis.ANIS.lecture.domain.entity.LectureRegistered;
import com.npt.anis.ANIS.lecture.domain.mapper.LectureMapper;
import com.npt.anis.ANIS.lecture.domain.mapper.LectureRegisteredMapper;
import com.npt.anis.ANIS.lecture.repository.LectureRegisteredRepository;
import com.npt.anis.ANIS.member.domain.dto.MemberCreateDTO;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class LectureRegisteredServiceImpl implements LectureRegisteredService {
    private final LectureRegisteredRepository lectureRegisteredRepository;
    private final LectureMapper lectureMapper;
    private final LectureRegisteredMapper lectureRegisteredMapper;


    //TODO 최대학점 최소학점 프론트에서 거르기(일단 있는기능은 냅둠)

    @Override
    public void createLectureRegistered(LectureRegisteredDto lectureRegisteredDto) {
        LectureRegistered lectureRegistered = lectureRegisteredMapper.toEntity(lectureRegisteredDto);
        lectureRegisteredRepository.save(lectureRegistered);
    }
    /***
     * 수강신청할때 연관관계가 없어서 수강신청과목이 겹칠수있는 가능성을 배제하기위해 만든 메서드
     * @param studentID 해당 학생의ID
     */
    public void clearLectureRegistered(String studentID){
        List<LectureRegistered> lectureRegisteredList = lectureRegisteredRepository.findByStudentID(studentID);
        for (LectureRegistered lectureRegistered : lectureRegisteredList) {
            lectureRegisteredRepository.delete(lectureRegistered);
        }
    }
    /***
     * 최소학점과 최대학점에 따라서 수강신청 가능한지 안한지에대한 검증후에 수강신청함
     * @param studentID 현재 수강하려는 학생의 ID
     * @param lectureListDto 수강하려는 수강과목들
     * @return
     */
    @Override
    public boolean lectureRegistered(String studentID, List<LectureDto> lectureListDto) {
        List<Lecture> lectureList = lectureMapper.toEntities(lectureListDto);
        int lecCredit = 0;
        // 현재 수강하려는 총 학점을 계산
        for (Lecture lecture : lectureList) {
            lecCredit += lecture.getLecCredit();
        }
        if (12 <= lecCredit && lecCredit <= 22) {
            // lecID가 겹치는걸 해결하기위해 로그인한 학생이 수강되어있는 과목들을 모두 지워버림
            clearLectureRegistered(studentID);
            for (Lecture lecture : lectureList) {
                createLectureRegistered(new LectureRegisteredDto( lecture.getLecID(), studentID));
            }
            return true;
        }else{
            return false;
        }
    }

    /***
     * 친구의 수강신청을 따라하는 메서드
     * @param friendID 친구의 ID
     * @param myID 나의 ID
     * @return
     */
    @Override
    public boolean lectureRegisteredWithFriend(String myID,String friendID){
        // 친구의 수강신청 목록 받아오기
        List<LectureDto> studentLectureList = studentLectureList(friendID);
        // 친구의 시간표로 수강신청하기
        return lectureRegistered(myID,studentLectureList);
    }

    /***
     * 친구의 수강신청 목록 받아오기
     * @param studentID 친구의 id
     * @return 친구의 수강신청 리스트 반환
     */
    @Override
    public List<LectureDto> studentLectureList(String studentID){
        List<Tuple> tupleList = lectureRegisteredRepository.getByLectureRegisteredWithLecture(studentID);
        List<Lecture> lectureList = new ArrayList<>();
        // Lecture 클래스타입으로 가공
        for (Tuple tuple : tupleList) {
            Lecture lecture = tuple.get("lecture", Lecture.class);
            lectureList.add(lecture);
        }
        return lectureMapper.toDtos(lectureList);
    }

    /***
     * 나의 시간표로 친구들의 시간표를 바꿈
     * @param friendList 나의 시간표로 수강신청을 바꿀 학생들
     * @param myID 나의 아이디
     * @return 성공하면 true 실패하면 false
     */
    @Override
    public boolean lectureRegisteredWithFriends(List<MemberCreateDTO> friendList, String myID){
        // 나의 수강신청 목록 갖고오기
        for (MemberCreateDTO memberDto:friendList) {
            lectureRegisteredWithFriend(memberDto.getStudentID(),myID);
        }
        return true;
    }
}
