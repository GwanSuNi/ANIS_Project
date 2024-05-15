package com.npt.anis.ANIS.member.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberSearchDTO {
    private String studentID;
    private String studentName;
    private String birth;
    private String departmentName;
}
