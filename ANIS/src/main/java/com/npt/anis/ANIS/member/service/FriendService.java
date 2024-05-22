package com.npt.anis.ANIS.member.service;

import com.npt.anis.ANIS.member.domain.dto.FriendDto;
import com.npt.anis.ANIS.member.domain.dto.MemberSearchDTO;
import org.springframework.stereotype.Service;

import java.util.List;
public interface FriendService {
    public FriendDto createFriend(String friendID, String myID);
    public List<MemberSearchDTO> getMyFriends(String userId);
    public boolean softDeleteFriend(String friendID,String myID);
}
