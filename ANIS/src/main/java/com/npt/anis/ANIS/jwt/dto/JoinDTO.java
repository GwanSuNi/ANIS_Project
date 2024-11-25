package com.npt.anis.ANIS.jwt.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
public class JoinDTO {
    private String username;
    private String password;
    private String studentName;
    private int grade;
    private String departmentId;
    private String birth;
    private String role;
}