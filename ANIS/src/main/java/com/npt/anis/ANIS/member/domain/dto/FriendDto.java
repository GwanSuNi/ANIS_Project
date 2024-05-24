package com.npt.anis.ANIS.member.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendDto {
    private Long frIndex;
    // 나의 ID Member와 연관관계 되어있음
    private String myStuID;
    // 친구의 ID
    private String frStuID;
    // 친구가 삭제되었는지 안되었는지
    // 처음에 친구가 되어있지않다면 데이터를 만들고 친구로 되어있다면 addState를 true 바꾸기
    private boolean addState;
}
