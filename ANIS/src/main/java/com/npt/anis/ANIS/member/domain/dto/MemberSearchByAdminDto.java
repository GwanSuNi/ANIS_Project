package com.npt.anis.ANIS.member.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@RequiredArgsConstructor
public class MemberSearchByAdminDto {
    private int studentID;
    private String depName;
    private String studentName;
    private String birth;
    private String lastLogin;
    private String isQuit;
    private String role;

    // JPQL에서는 원본의 데이터를 가져와서 데이터 가공은 생성자나 서비스 레이어에서 원하는 대로 하면 됨
    public MemberSearchByAdminDto(String studentID, String depName, String studentName, String birth, LocalDateTime lastLogin, boolean isQuit, String role) {
        this.studentID = Integer.parseInt(studentID);
        this.depName = depName;
        this.studentName = studentName;
        this.birth = birth.replaceAll("(\\d{4})(\\d{2})(\\d{2})", "$1-$2-$3");
        this.lastLogin = lastLogin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.isQuit = isQuit ? "탈퇴" : "정상";
        this.role = role.contains("ADMIN") ? "관리자": "학생";
    }
}
