package com.npt.anis.ANIS.member.domain.dto;

import com.mongodb.annotations.Sealed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@Sealed
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateDTO {
    private String studentID;
    private String studentName;
    private String password;
    private String birth;
    private String role;
    private Long DepartmentId;
    private LocalDateTime lastLogin = LocalDateTime.now();
    private boolean isQuit = false;
}
