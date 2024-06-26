package com.npt.anis.ANIS.lecture.service;

import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.domain.dto.LectureRegisteredDto;
import com.npt.anis.ANIS.member.domain.dto.MemberCreateDTO;
import com.npt.anis.ANIS.member.domain.dto.MemberSearchDTO;

import java.util.List;

public interface LectureRegisteredService {
    // 강의 하나에대한 수강신청 -> 다른 수강신청 서비스에 의존적
    public void createLectureRegistered(LectureRegisteredDto lectureRegisteredDto);
    // 수강신청시 연관관계가 없어 여러가지를 수강신청할 수 있기 때문에 값의 중복을 배제하기위한 메서드
    public void clearLectureRegistered(String studentID);
    // 실제 수강신청하기 서비스 수강신청할 학생 ID, 수강신청할 수강 리스트
    public boolean lectureRegistered(String studentID, List<LectureDto> lectureListDto);
    // 친구의 수강신청 따라하는 서비스 개인 -> 친구 (나의(myID) 시간표가 친구(friendID)의 시간표와 똑같아짐)
    public boolean lectureRegisteredWithFriend(String myID,String friendID);
    // 친구와 수강신청 함께하기 서비스 개인-> 단체 (나의(myID) 시간표와 친구(들)의 시간표가 똑같아짐)
    public boolean lectureRegisteredWithFriends(List<MemberSearchDTO> friendList, String myID);
    // 학생의 수강신청 목록 받아오기
    public List<LectureDto> studentLectureList(String studentID);
    // 입력받은 강의 리스트가 lpIndex가 null 인 강의 리스트로 바꾸는 메서드(데이터의 일관성을 위해 만들어진 메서드)
    public List<LectureDto> lpIndexNullLectureList(List<LectureDto> lectureDtoList);
    // 현재 학생이 수강하고있는 LectureRegistered 갖고오기
    public List<LectureRegisteredDto> getCurrentSemesterLecturesRegistered(String studentID);
    // 현재 학생의 수강신청을 업데이트하는 메서드 , 혹은 처음수강신청일 경우 추가해주는 메서드
    public boolean updateLectureRegistrations(List<LectureRegisteredDto> lectureRegisteredDtoList, List<LectureDto> lectureDtoList,String studentID);
}
