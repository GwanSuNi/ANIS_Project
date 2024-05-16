package com.npt.anis.ANIS.member.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private String studentID;
    private String studentName;
    private String birth;
    private String role;
    private String departmentID;
    private LocalDateTime lastLogin;
    private boolean isQuit;
}
