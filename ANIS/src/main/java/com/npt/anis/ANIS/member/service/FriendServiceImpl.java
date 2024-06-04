package com.npt.anis.ANIS.member.service;

import com.npt.anis.ANIS.global.exception.ResourceNotFoundException;
import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.exception.NotFoundLectureException;
import com.npt.anis.ANIS.member.domain.dto.FriendDto;
import com.npt.anis.ANIS.member.domain.dto.MemberDTO;
import com.npt.anis.ANIS.member.domain.dto.MemberSearchDTO;
import com.npt.anis.ANIS.member.domain.entity.Friend;
import com.npt.anis.ANIS.member.domain.entity.Member;
import com.npt.anis.ANIS.member.mapper.FriendMapper;
import com.npt.anis.ANIS.member.mapper.MemberMapper;
import com.npt.anis.ANIS.member.repository.FriendRepository;
import com.npt.anis.ANIS.member.repository.MemberRepository;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service("friendServiceImpl")
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;
    private final MemberService memberService;

    /**
     * 현재 나의 친구목록에 친구가 있었는지 없었는지 확인한 후에 과거에 친구목록에 추가했었다면
     * AddState 만 false 로 바꿈
     *
     * @param friendID 친구의 아이디
     * @param myID     나의 아이디
     * @return
     */
    @Override
    @Transactional
    public FriendDto createFriend(String friendID, String myID) {
        if (friendRepository.existsByFrStuIDAndMyStuID(friendID, myID)) {
            Friend friend = friendRepository.findByFrStuIDAndMyStuID(friendID, myID);
            friend.setAddState(true);
            return friendMapper.toDto(Optional.of(friend));
        } else {
            Friend newFriend = new Friend();
            newFriend.setFrStuID(friendID);
            newFriend.setMyStuID(myID);
            newFriend.setAddState(true);
            friendRepository.save(newFriend);
            return friendMapper.toDto(Optional.of(newFriend));
        }
    }

    /**
     * 친구목록에 데이터가 있는 상태에서 친구의 데이터를 받아온후, 친구의 상태를 false로 바꿔주기
     *
     * @param friendID 친구아이디
     * @param myID     나의아이디
     */
    @Override
    public boolean softDeleteFriend(String friendID, String myID) {
        Friend friend = friendRepository.findByFrStuIDAndMyStuID(friendID, myID);
        if (friend != null) {
            friend.setAddState(false);
            friendRepository.save(friend);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 현재 로그인 되어있는 id의 친구 목록 불러오기
     * @param myID 현재 로그인되어있는 유저의 ID
     * @return
     */
    @Override
    public List<MemberSearchDTO> getMyFriends(String myID) {
        List<Friend> friends = friendRepository.findByMyStuID(myID);
        List<MemberSearchDTO> friendList = new ArrayList<>();
        for (Friend friend : friends) {
            friendList.add(memberService.getMemberSearch(friend.getFrStuID()));
        }
        // 친구 목록에서 나의 아이디는 제외하기
        return friendList;
    }
}
