package com.npt.anis.ANIS.lecture.service;

import com.npt.anis.ANIS.global.util.DateUtils;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("lectureRegisteredServiceImpl")
public class LectureRegisteredServiceImpl implements LectureRegisteredService {
    private final LectureRegisteredRepository lectureRegisteredRepository;
    private final LectureMapper lectureMapper;
    private final LectureRegisteredMapper lectureRegisteredMapper;
    @Qualifier("lectureServiceImpl")
    private final LectureService lectureService;


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
//        수강신청하는 LectureList의 lpIndex 가 null 인 애들로 바꾸는 메서드
        List<LectureDto> lectureDtoList = lpIndexNullLectureList(lectureListDto);
//        현재 수강 하고있는 강의 리스트를 받아오는 메서드
        List<LectureRegisteredDto> lectureRegisteredDtoList = getCurrentSemesterLecturesRegistered(studentID);
        return updateLectureRegistrations(lectureRegisteredDtoList,lectureDtoList,studentID);
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
        List<LectureDto> friendLectureList = studentLectureList(friendID);
        // 친구의 시간표로 수강신청하기
        return lectureRegistered(myID,friendLectureList);
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
            Boolean isSelected = tuple.get("selected", Boolean.class);
            Integer lecYear = tuple.get("lecYear", Integer.class);
            Integer lecSemester = tuple.get("lecSemester", Integer.class);

            // selected가 true이고, lecYear와 lecSemester가 현재 년도와 학기인 경우에만 Lecture 객체를 추가합니다.
            if (isSelected != null && isSelected && DateUtils.isCurrentSemester(lecSemester, lecYear)) {
                Lecture lecture = tuple.get("lecture", Lecture.class);
                lectureList.add(lecture);
            }
        }
        return lectureMapper.toDtos(lectureList);
    }

    /***
     * 친구들의 시간표를 나의 시간표로 바꿈
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

    /***
     * 수강신청전에 받아온 lecture들을 lpIndex가 Null 인 강의로들만 갖고오는 메서드
     * 수강신청을 할때 lecturePreset 으로 수강신청할 경우 보여주는 강의들은 lecture안에 lpIndex가 존재하는 LectureList 이지만
     * 데이터의 일관성을 위해서 수강신청을 할때는 Lecture의 lpIndex가 null인 Lecture들만 수강신청하게 만듬
     * @param lectureDtoList
     * @return
     */
    @Override
    public List<LectureDto> lpIndexNullLectureList(List<LectureDto> lectureDtoList){
        List<LectureDto> nullLectureList = lectureService.showAvailableLectureList();
        // Set을 사용한 이유는 Set의 contains 메소드가 일반적으로 시간 복잡도가 O(1)이기 때문에 사용
        // notNullLectureList의 각 LectureDto에서 강의 이름을 추출하고,
        // 이를 notNullLectureNames라는 Set에 저장합
        Set<String> notNullLectureNames = lectureDtoList.stream()
                .map(LectureDto::getLecName)
                .collect(Collectors.toSet());
        // nullLectureList의 각 LectureDto에서 강의 이름이 notNullLectureNames 집합에 포함되어 있는지 확인하고,
        // 포함되어 있는 경우 해당 LectureDto를 matchingLectures 리스트에 추가
        List<LectureDto> matchingLectures = nullLectureList.stream()
                .filter(nullLecture -> notNullLectureNames.contains(nullLecture.getLecName()))
                .collect(Collectors.toList());
        return matchingLectures;
    }

    /***
     * 현재 년도와 학기에 수강하고있는 학생의 강의 갖고오기
     * @param studentID 현재 수강한 학생 ID
     * @return
     */
    @Override
    public List<LectureRegisteredDto> getCurrentSemesterLecturesRegistered(String studentID) {
        // 학생이 수강했었던 모든 강의 들을 갖고오기
        List<LectureRegisteredDto> lectureRegisteredDtoList = lectureRegisteredMapper.toDtos(lectureRegisteredRepository.findByStudentID(studentID));
        // 현재 년도와 학기에 수강하고있는 학생의 강의 갖고오기
        List<LectureRegisteredDto> currentLectures = lectureRegisteredDtoList.stream()
                .filter(lecture -> DateUtils.isCurrentSemester(lecture.getLecSemester(), lecture.getLecYear()))
                .collect(Collectors.toList());

        return currentLectures;
    }

    /*** TODO 에러처리하기
     * 수강되어있는 LectureRegistered 를 참고하여 매개변수로 입력받은 LectureDtoList 를 업데이트 하는 메서드
     * @param lectureRegisteredDtoList 현재 studentID가 등록되어있는 강의 리스트 받아오기
     * @param lectureDtoList 내가 바꾸려는 LectureDtoList 받아오기
     * @param studentID 현재 로그인한 학생 ID 받아오기
     * @return
     */
    @Override
    public boolean updateLectureRegistrations(List<LectureRegisteredDto> lectureRegisteredDtoList, List<LectureDto> lectureDtoList,String studentID) {
        // lecID를 키로 사용하여 LectureRegisteredDto 객체를 저장하는 맵을 생성합니다.
        Map<Long, LectureRegisteredDto> lectureRegisteredMap = lectureRegisteredDtoList.stream()
                .collect(Collectors.toMap(LectureRegisteredDto::getLecID, Function.identity()));

        for (LectureDto lectureDto : lectureDtoList) {
            LectureRegisteredDto lectureRegisteredDto = lectureRegisteredMap.get(lectureDto.getLecID());

            if (lectureRegisteredDto == null) {
                // 새로운 LectureRegisteredDto 객체를 생성하고, lecID와 selected 상태를 설정합니다.
                lectureRegisteredDto = new LectureRegisteredDto();
                lectureRegisteredDto.setLecID(lectureDto.getLecID());
                lectureRegisteredDto.setSelected(true);
                lectureRegisteredDto.setStudentID(studentID);
                // 생성된 객체를 리스트에 추가합니다.
                lectureRegisteredDtoList.add(lectureRegisteredDto);
            } else {
                // 이미 존재하는 LectureRegisteredDto 객체의 selected 상태를 true로 설정합니다.
                lectureRegisteredDto.setSelected(true);
            }
        }

        // lectureDtoList에 없는 lecID를 가진 LectureRegisteredDto 객체의 selected 상태를 false로 설정합니다.
        lectureRegisteredDtoList.stream()
                .filter(lectureRegisteredDto -> !lectureDtoList.stream().anyMatch(lectureDto -> lectureDto.getLecID().equals(lectureRegisteredDto.getLecID())))
                .forEach(lectureRegisteredDto -> lectureRegisteredDto.setSelected(false));

        for (LectureRegisteredDto lectureRegisteredDto : lectureRegisteredDtoList) {
            lectureRegisteredRepository.save(lectureRegisteredMapper.toEntity(lectureRegisteredDto));
        }
        return true;
    }
}
