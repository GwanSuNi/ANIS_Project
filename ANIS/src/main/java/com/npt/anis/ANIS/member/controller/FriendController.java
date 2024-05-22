package com.npt.anis.ANIS.member.controller;

import com.npt.anis.ANIS.member.domain.dto.FriendDto;
import com.npt.anis.ANIS.member.domain.dto.MemberSearchDTO;
import com.npt.anis.ANIS.member.domain.entity.Friend;
import com.npt.anis.ANIS.member.service.FriendService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class FriendController {
    @Qualifier("friendServiceImpl")
    private final FriendService friendService;
//            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    /**
     * 친구 삭제하기
     * @param friendID 삭제 할 친구의 아이디
     * @return 삭제했는지 못했는지 메세지 분기
     */
    @PatchMapping("/friend/{friendID}")
    public ResponseEntity<String> softDeleteFriend(@PathVariable("friendID") String friendID) {
         String userID = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean success = friendService.softDeleteFriend(friendID, userID);

        if (success) {
            return ResponseEntity.ok("친구를 삭제했습니다.");
        } else {
            return ResponseEntity.status(500).body("친구를 삭제하지 못했습니다.");
        }
    }

    /**
     * 현재 로그인한 유저의 친구 리스트 받아오기
     * @return MemberSearchDTO 리스트 반환
     */
    @GetMapping("/friendList")
    public ResponseEntity<List<MemberSearchDTO>> friendList() {
        String userID = SecurityContextHolder.getContext().getAuthentication().getName();
        List<MemberSearchDTO> friendList = friendService.getMyFriends(userID);
        return ResponseEntity.ok(friendList);
    }

    /**
     * TODO 에러처리
     * 친구의 아이디를 받아와서 친구추가하기
     * @param friendID 친구의 ID 받아오기
     * @return FriendDto 반환
     */
    @PostMapping("/friend/{friendID}")
    public ResponseEntity<FriendDto> addFriend(@PathVariable("friendID") String friendID) {
         String userID = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(friendService.createFriend(friendID,userID));
    }
}
