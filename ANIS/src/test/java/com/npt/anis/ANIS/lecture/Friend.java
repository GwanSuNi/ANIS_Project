package com.npt.anis.ANIS.lecture;

import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.domain.dto.LecturePresetDto;
import com.npt.anis.ANIS.lecture.service.LecturePresetServiceImpl;
import com.npt.anis.ANIS.lecture.service.LectureServiceImpl;
import com.npt.anis.ANIS.member.domain.dto.MemberSearchDTO;
import com.npt.anis.ANIS.member.mapper.FriendMapper;
import com.npt.anis.ANIS.member.repository.FriendRepository;
import com.npt.anis.ANIS.member.service.FriendService;
import com.npt.anis.ANIS.member.service.FriendServiceImpl;
import com.npt.anis.ANIS.member.service.MemberService;
import jakarta.persistence.Access;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@ExtendWith(MockitoExtension.class) // Mockito를 사용하여 Mock 객체를 주입할 경우
//@RunWith(SpringRunner.class)
@SpringBootTest
public class Friend {
    @Autowired
    private FriendService friendService;
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private  FriendMapper friendMapper;
    @Autowired
    private  MemberService memberService;

    @BeforeEach
    public void dependencySetting(){
        friendService = new FriendServiceImpl(friendRepository,friendMapper,memberService);
    }
    @Test
    @DisplayName("친구테스트")
    public void createTest(){
        List<MemberSearchDTO> memberSearchDTOList = friendService.getMyFriends("20244003");
        for (int i = 0; i < memberSearchDTOList.size(); i++) {
            System.out.println("============="+memberSearchDTOList.get(i).getDepartmentName()+"==============");
            System.out.println("============="+memberSearchDTOList.get(i).getStudentID()+"==============");
            System.out.println("============="+memberSearchDTOList.get(i).getStudentName()+"==============");

        }
    }
}
